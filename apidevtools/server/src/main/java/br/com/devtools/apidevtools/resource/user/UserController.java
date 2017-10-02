package br.com.devtools.apidevtools.resource.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.crypto.Crypto;
import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.resource.user.acess.artifact.Acess;
import br.com.devtools.apidevtools.resource.user.acess.artifact.AcessStatus;
import br.com.devtools.apidevtools.resource.user.permission.PermissionController;
import br.com.devtools.apidevtools.resource.user.permission.PermissionGroup;

@Path("user")
@PermissionClass(description = "Usuário")
public class UserController extends Controller<User> {

	private static final String ALTERAACESSO = "ALTERAACESSO";
	private static final String ALTERAPERMISSAO = "ALTERAPERMISSAO";
	private static final String ATIVACAO = "ATIVACAO";
	
	@Override
	public Class<User> getClasse() {
		return User.class;
	}
	
	@Override
	protected void beforePost(User user) throws RestException {
		user.setCreation(LocalDateTime.now());
	}
	

	@PUT
	@Path("{id}/kill")
	@PermissionMethod(types=ATIVACAO, description="Desativar Componente")
	public User kill(@PathParam("id") Long id) throws Exception {
		
		try {
			
			User user = this.get(id);
			user.setDeath(LocalDateTime.now());
			return this.put(id, user);
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@PUT
	@Path("{id}/revive")
	@PermissionMethod(types=ATIVACAO, description="Reativar Usuário")
	public User revive(@PathParam("id") Long id) throws Exception {
		
		try {
			
			User user = this.get(id);
			user.setDeath(null);
			return this.put(id, user);
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	
	@POST
	@Path("{id}/acess")
	@PermissionMethod(types = ALTERAACESSO, description = "Altera Acesso ao Sistema")
	public void createAcess(@PathParam("id") Long id, Acess acess) throws RestException {
		try {
			
			Crypto crypto = new Crypto();
			LocalDateTime now = LocalDateTime.now();
			User user = this.get(id);

			TypedQuery<Acess> query = this.getSessao().getEm().createQuery(
					" select a from Acess a " + " where a.user = :user " + " and a.status = :status ", Acess.class);
			query.setParameter("user", user);
			query.setParameter("status", AcessStatus.ACTIVE);

			List<Acess> list = query.getResultList();
			list.forEach(old -> {
				old.setStatus(AcessStatus.INACTIVE);
				old.setDeath(now);
				this.getSessao().getEm().merge(old);
			});

			acess.setUser(user);
			acess.setCreation(now);
			acess.setStatus(AcessStatus.ACTIVE);
			acess.setHash(crypto.criptografar(acess.getName()) + crypto.criptografar(acess.getPassword()));

			this.getSessao().getEm().persist(acess);

			this.getSessao().commit();

		} catch (Exception e) {
			throw new RestException(e);
		}
	}
	
	@GET
	@Path("{id}/permission")
	@PermissionMethod(types = ALTERAPERMISSAO, description = "Visializa Grupo de Permissão do Usuário")
	public List<PermissionGroup> getPermission(@PathParam("id") Long id) throws RestException {
		
		try {
			
			User user = this.get(id);
			
			TypedQuery<PermissionGroup> query = this.getSessao().getEm().createQuery(
					" select upg.grupo from UserPermissionGroup upg "
					+ " where upg.user = :user ",
					PermissionGroup.class);
			
			query.setParameter("user", user);
			
			return query.getResultList();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@POST
	@Path("{id}/permission/{permissionId}")
	@PermissionMethod(types = ALTERAPERMISSAO, description = "Adiciona Grupo de Permissão ao Usuário")
	public void addPermission(@PathParam("id") Long id, @PathParam("id") Long permissaoId) throws RestException {
		
		try {
			
			User user = this.get(id);
			
			PermissionController pController = new PermissionController();
			pController.setSessao(this.getSessao());
			pController.setContext(this.getContext());
			
			PermissionGroup permissionGroup = pController.get(permissaoId);
			
			TypedQuery<UserPermissionGroup> query = this.getSessao().getEm().createQuery(
					" select upg from UserPermissionGroup upg  "
					+ " where upg.user = :user "
					+ " and upg.grupo = :grupo ",
					UserPermissionGroup.class);
			
			query.setParameter("user", user);
			query.setParameter("grupo", permissionGroup);
			
			UserPermissionGroup upg;
			
			try {
				upg = query.getSingleResult();
			} catch (NoResultException e) {
				upg = null;
			}
			
			if (upg==null) {
				
				upg = new UserPermissionGroup();
				upg.setUser(user);
				upg.setGrupo(permissionGroup);
				
				this.getEm().persist(upg);
				
				this.getSessao().commit();
				
			}
			
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	@DELETE
	@Path("{id}/permission/{permissionId}")
	@PermissionMethod(types = ALTERAPERMISSAO, description = "Remove Grupo de Permissão ao Usuário")
	public void removePermission(@PathParam("id") Long id, @PathParam("id") Long permissaoId) throws RestException {
		
		try {
			
			User user = this.get(id);
			
			PermissionController pController = new PermissionController();
			pController.setSessao(this.getSessao());
			pController.setContext(this.getContext());
			
			PermissionGroup permissionGroup = pController.get(permissaoId);
			
			TypedQuery<UserPermissionGroup> query = this.getSessao().getEm().createQuery(
					" select upg from UserPermissionGroup upg  "
					+ " where upg.user = :user "
					+ " and upg.grupo = :grupo ",
					UserPermissionGroup.class);
			
			query.setParameter("user", user);
			query.setParameter("grupo", permissionGroup);
			
			UserPermissionGroup upg = query.getSingleResult();
			
			if (upg!=null) {
				
				this.getEm().remove(upg);
				this.getSessao().commit();
				
			}
			
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
}
