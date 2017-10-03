package br.com.devtools.apidevtools.core.rest.filters;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ResourceMethodInvoker;

import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.user.UserPermissionGroup;
import br.com.devtools.apidevtools.resource.user.UserType;
import br.com.devtools.apidevtools.resource.user.acess.artifact.AcessStatus;
import br.com.devtools.apidevtools.resource.user.acess.artifact.Session;
import br.com.devtools.apidevtools.resource.user.permission.Permission;

@Provider
public class RequestFilter implements ContainerRequestFilter {

	@Inject
	RestSessao sessao;
	
	@Context
	HttpServletRequest context;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		try {
			
			if (requestContext.getUriInfo().getAbsolutePath().toString().indexOf("/api/swagger.json")>0) {
				return ;
			}
			
			ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) 
		            requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
			
			PermissionClass pClass = methodInvoker.getResourceClass().getDeclaredAnnotation(PermissionClass.class);
			
			PermissionMethod pMethod = null;
			
			for (Annotation annotation : methodInvoker.getMethodAnnotations()) {
				if (annotation.annotationType().equals(PermissionMethod.class)) {
					pMethod = (PermissionMethod) annotation;
				}
			}
			
			if (pClass==null) {
				throw new IOException("Sem configuração de Permisão na classe");
			}
			
			if (pMethod==null && pClass.allMethods().length()==0) {
				throw new IOException("Sem configuração de Permisão no método");
			}
			
			
			//String userToken = context.getHeader("user-token");
			
			Session session = new Session();
			session.setCreation(LocalDateTime.now());
			session.setIp(context.getRemoteHost()+"-"+context.getRemoteAddr());
			session.setUseragent(context.getHeader("User-Agent"));
			
			//UserToken token = new UserToken();
			//token.split(userToken, sessao);
			
			sessao.setSession(session);
			
			String sessionToken = context.getHeader("session-token");
			
			if (sessionToken!=null && sessionToken.length()>0) {
				
				TypedQuery<Session> query = sessao.getEm().createQuery(
						" select s from Session s " +
						" inner join s.acess a " +
						" where a.status = :status " +
						" and s.hash = :hash " +
						" and s.ip = :ip " +
						" and s.useragent = :useragent "
						, Session.class);
				
				try {
					query.setParameter("status", AcessStatus.ACTIVE);
					//query.setParameter("userhash", token.userCrypto(sessao));
					query.setParameter("hash", sessionToken);
					query.setParameter("ip", session.getIp());
					query.setParameter("useragent", session.getUseragent());
				} catch (Exception e) {
					throw new IOException(e);
				}
				
				Session s = query.getSingleResult();
				
				if (
					s==null ||
					!s.getUseragent().equals(session.getUseragent()) ||
					!s.getIp().equals(session.getIp()) ||
					s.getAcess().getDeath()!=null
				) {
					throw new IOException();
				}
				
				sessao.setSession(s);
				
			}

			if ((pMethod==null && pClass.allMethods().equals("ALL")) || Arrays.asList(pMethod.types()).contains("ALL")) {
				return;
			}

			if (sessao.getSession().getAcess().getUser().getType().equals(UserType.ADMIN)) {
				return;
			} else {
				
				TypedQuery<UserPermissionGroup> queryGroupPermissions = sessao.getEm().createQuery(
						" select p from UserPermissionGroup p " +
						" where p.user = :user "
						, UserPermissionGroup.class);
				
				queryGroupPermissions.setParameter("user", sessao.getSession().getAcess().getUser());
				
				for (UserPermissionGroup upg : queryGroupPermissions.getResultList()) {
					for (Permission p : upg.getGrupo().getPermissions()) {
						if (p.getClassName().equals(methodInvoker.getResourceClass().getName())) {
							if (Arrays.asList(pMethod.types()).contains(p.getAuthorize())) {
								return;
							}
						}
					}
				}
				
			}
			
			throw new IOException("Acesso negado");
			
		} catch (Exception e) {
			throw new IOException("Acesso negado");
		}
		
	}
	
}