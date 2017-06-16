package br.com.devtools.apidevtools.core.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;

import br.com.devtools.apidevtools.core.help.HelpGenerator;
import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.core.rulemanager.RuleManager;
import br.com.devtools.apidevtools.core.rulemanager.rules.RuleDelete;
import br.com.devtools.apidevtools.core.rulemanager.rules.RuleGet;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.resource.revinfo.RevInfo;
import br.com.devtools.apidevtools.resource.revinfo.RevInfoResult;

@SuppressWarnings("unchecked")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json;charset=UTF-8")
public abstract class Controller<Model> {
	
	public abstract Class<Model> getClasse();
	
	@Context
	HttpServletRequest context;
	
	@Inject
	RestSessao sessao;
	
	public EntityManager getEm() {
		return this.getSessao().getEm();
	}
	
	@SuppressWarnings("rawtypes")
	private List<RuleGet> getGetRules() throws Exception {
		return new RuleManager<>(this).getRules();
	}
	
	@SuppressWarnings("rawtypes")
	private List<RulePost> getPostRules() throws Exception {
		return new RuleManager<>(this).postRules();
	}
	
	@SuppressWarnings("rawtypes")
	private List<RulePut> getPutRules() throws Exception {
		return new RuleManager<>(this).putRules();
	}
	
	@SuppressWarnings("rawtypes")
	private List<RuleDelete> getDeleteRules() throws Exception {
		return new RuleManager<>(this).deleteRules();
	}
	
	private void setId(Model entidade, Long id) throws Exception {
		
		Field[] campos = getClasse().getDeclaredFields();
		for (Field field : campos) {
			
			Id pk = field.getAnnotation(Id.class);
			if (pk!=null) {
				field.setAccessible(true);
				field.set(entidade, id);
			}
			
		}
		
	}
	
	private String sql;
	@GET
	@Path("help")
	@Produces(MediaType.TEXT_HTML)
	public String help() {
		return new HelpGenerator().help(this.getClass(), this.getClasse());
	}
	
	@GET
	@Path("{id}/revision")
	public List<RevInfoResult<Model>> audited(@PathParam("id") Long id) {
	
		AuditReader reader = AuditReaderFactory.get(this.getSessao().getEm());

        List<Object[]> list = reader.createQuery()
                .forRevisionsOfEntity(this.getClasse(), false, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();
        
        List<RevInfoResult<Model>> revisions = new ArrayList<>();
        
        for (Object[] list2 : list) {
        	
        	RevInfoResult<Model> rev = new RevInfoResult<>();
        	
			for (Object object : list2) {
				if (this.getClasse().equals(object.getClass())) {
					Model o = (Model) object;
					rev.setObject(o);
				} else if (object instanceof RevInfo) {
					RevInfo info = (RevInfo) object;
					rev.setAcessId(info.getAcessId());
					rev.setAlteration(info.getAlteration());
					rev.setIp(info.getIp());
					rev.setPersonId(info.getPersonId());
				} else if (object instanceof RevisionType) {
					RevisionType type = (RevisionType) object;
					rev.setRevtype(type);
				}
				
			}
			
			revisions.add(rev);
			
		}
        
		return revisions;
		
	}
	
	@GET
	public FormGet<Model> get(@QueryParam("page") Integer page, @QueryParam("numberRecords") Integer numberRecords) throws RestException {
		
		try {
			
			for (RuleGet<Model> rule : this.getGetRules()) {
				rule.validate(this, null);
			}
			
			List<String> filterGet = new ArrayList<>();
			
			Collections.list(this.getContext().getParameterNames()).forEach(item -> {
				
				if (!"page".equalsIgnoreCase(""+item) && !"numberRecords".equalsIgnoreCase(""+item)) {
					
					String filter = " ";
					String valor = this.getContext().getParameter(""+item);
					String[] split = valor.split("\\|");
					if (split.length>1) {
						if ("like".equalsIgnoreCase(split[0])) {
							filter += "lower(" + item + ") " + split[0] + " '%" + split[1].toLowerCase()+"%' ";
						} else {
							filter += item + " " + split[0] + " '" + split[1]+"' ";
						}
					} else if (valor.equalsIgnoreCase("null")) {
						filter += item + " is null ";
					} else if (valor.equalsIgnoreCase("notnull")) {
						filter += item + " is not null ";
					} else {
						filter += item + " = '" + valor + "' ";
					}
					
					filterGet.add(filter);
					
				}
				
			});
			
			if (page==null || page<=0) {
				page = 1;
			}
			
			if (numberRecords==null || numberRecords<=0) {
				numberRecords = 20;
			}
			
			FormGet<Model> form = new FormGet<>();
			String name = this.getClasse().getSimpleName();
						
			this.sql = "from "+name + " m ";
			
			if (this.getFilters()!=null && this.getFilters().size()>0) {
				
				sql += " where ";
				
				this.getFilters().forEach(filtro -> {
					this.sql += ("m."+filtro.getName()+" = :"+filtro.getName());
				});
				
				if (filterGet!=null && filterGet.size()>0) {
					filterGet.forEach(item -> {
						this.sql += " and " + item;
					});
				}
			
				
			} else {
				
				if (filterGet!=null && filterGet.size()>0) {
					
					boolean first = true;
					for (String item : filterGet) {
						if (first) {
							first = false;
							sql += " where ";
						} else {
							sql += " and ";
						}
						this.sql += item;
					}
					
				}
			
			}
			
			String jpql = "SELECT count(m) " + this.sql;
			Query q = this.getEm().createQuery(jpql);
			
			if (this.getFilters()!=null && this.getFilters().size()>0) {
				this.getFilters().forEach(filtro -> {
					q.setParameter(filtro.getName(), filtro.getValue());
				});
			}
			
			Long totalRecords = (Long) q.getSingleResult();
			form.setTotalRecords(totalRecords);
			
			Long lastPage = totalRecords/numberRecords;
			if (totalRecords%numberRecords!=0) {
				lastPage++;
			}
			form.setLastPage(lastPage);
			form.setPage(page);
			form.setNumberRecords(numberRecords);
				
			this.sql += " order by id";
			
			TypedQuery<Model> query = this.getEm().createQuery(this.sql, this.getClasse());
			
			if (this.getFilters()!=null && this.getFilters().size()>0) {
				this.getFilters().forEach(filtro -> {
					query.setParameter(filtro.getName(), filtro.getValue());
				});
			}
			
			query.setFirstResult((page-1)*numberRecords);
			query.setMaxResults(numberRecords);
			List<Model> list = query.getResultList();
			
			form.setList(list);
			
			return form;
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@GET
	@Path("{id}")
	public Model get(@PathParam("id") Long id) throws RestException {
		
		try {
			
			for (RuleGet<Model> rule : this.getGetRules()) {
				rule.validate(this, null);
			}
			
			this.beforeGet(id);
			Model model = this.getEm().find(this.getClasse(), id);
			return this.afterGet(model);
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	@POST
	public Model post(Model model) throws RestException {
		
		try {

			for (RulePost<Model> rule : this.getPostRules()) {
				rule.validate(this, model);
			}
			
			this.beforePost(model);
			this.getEm().persist(model);
			this.getSessao().commit();
			
		} catch (RestException e) {
			throw e;
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		return this.afterPost(model);
		
	}
	
	@PUT
	@Path("{id}")
	public Model put(@PathParam("id") Long id, Model model) throws RestException {
		
		try {

			for (RulePut<Model> rule : this.getPutRules()) {
				rule.validate(this, model);
			}
			
			this.setId(model, id);
			this.beforePut(model);
			this.getEm().merge(model);
			this.getSessao().commit();
			
		} catch (RestException e) {
			throw e;
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		return this.afterPut(model);
		
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) throws RestException {
		
		try {
			
			Model model = this.get(id);
			
			for (RuleDelete<Model> rule : this.getDeleteRules()) {
				rule.validate(this, model);
			}
			
			this.beforeRemove(model);
			this.getEm().remove(model);
			this.getSessao().commit();
			this.afterRemove(model);
			
			return Response.ok().build();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
	}
	
	protected List<Filter> getFilters() throws RestException {
		return null;
	}
	
	protected void beforeGet(Long id) throws RestException {
	}
	protected Model afterGet(Model model) throws RestException {
		return model;
	}
	
	protected void beforePost(Model model) throws RestException {
	}
	protected Model afterPost(Model model) throws RestException {
		return model;
	}
	
	protected void beforePut(Model model) throws RestException {
	}
	protected Model afterPut(Model model) throws RestException {
		return model;
	}
	
	protected void beforeRemove(Model model) throws RestException {
	}
	protected Model afterRemove(Model model) throws RestException {
		return model;
	}

	public RestSessao getSessao() {
		return sessao;
	}

	public void setSessao(RestSessao sessao) {
		this.sessao = sessao;
	}

	public HttpServletRequest getContext() {
		return context;
	}

	public void setContext(HttpServletRequest context) {
		this.context = context;
	}
	
}
