package br.com.devtools.apidevtools.core.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.devtools.apidevtools.core.rest.RestException;
import br.com.devtools.apidevtools.core.rest.RestSessao;
import br.com.devtools.apidevtools.resource.help.HelpController;

@Produces("application/json;charset=UTF-8")
@Consumes("application/json;charset=UTF-8")
public abstract class Controller<Model> {
	
	protected abstract Class<Model> getClasse();
	
	@Context
	HttpServletRequest context;
	
	@Inject
	RestSessao sessao;
	
	private EntityManager getEm() {
		return this.getSessao().getEm();
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
		
		String html = "";
		html += "-- " + this.getClass().getSimpleName() + " --<br><br>";
		html += "<a href=\"http://localhost:8080/apidevtools/api/help\">Voltar</a><br><br>";
		
		Path annotation = this.getClass().getAnnotation(Path.class);
		
		String url = HelpController.baseUrl + annotation.value();
		
		html += url + "<br><br>";
		
		
		Method[] methods = this.getClass().getMethods();
		for (Method method : methods) {
			
			String methodName = "";
			
			if (method.isAnnotationPresent(Path.class)) {
				Path path = method.getAnnotation(Path.class);
				
				if (path.value().indexOf("help")>=0) {
					continue;
				}
				
				methodName = url +"/"+ path.value() + "<br>";
			} else {
				methodName = url + "<br>";
			}
			
			GET get = method.getAnnotation(GET.class);
			POST post = method.getAnnotation(POST.class);
			PUT put = method.getAnnotation(PUT.class);
			DELETE delete = method.getAnnotation(DELETE.class);
			
			if (get!=null || post!=null || put!=null || delete!=null) {
					

				if (get!=null) {
					html += "GET &emsp;&emsp;&emsp;";
				}
				if (post!=null) {
					html += "POST &emsp;&emsp; ";
				}
				if (put!=null) {
					html += "PUT &emsp;&emsp;&emsp;";
				}
				if (delete!=null) {
					html += "DELETE &emsp;";
				}
				
				html += methodName;
				
				
				Parameter[] parameters = method.getParameters();
				if (parameters!=null && parameters.length>0) {
					
					html += "&emsp;Request:<br>";
					
					for (Parameter param : parameters) {
						
						
						PathParam pathParam = param.getAnnotation(PathParam.class);
						QueryParam queryParam = param.getAnnotation(QueryParam.class);
						if (pathParam!=null) {
							//html += "&emsp;" + pathParam.value() + ":" + param.getType().getSimpleName()+"<br>";
						} else  if (queryParam!=null) {
							html += "&emsp;&emsp;" + queryParam.value() + ":" + param.getType().getSimpleName()+"<br>";
						} else if (param.getParameterizedType()!=null && param.getParameterizedType().getTypeName().equals("Model")) {
							
							html += "&emsp;&emsp;" + this.getClasse().getSimpleName() + " : ";
							try {
								
								ObjectMapper mapper = new ObjectMapper();
								Object obj = this.getClasse().newInstance();
								String jsonInString = mapper.writeValueAsString(obj);
								html += jsonInString;
							} catch (Exception e) {
							}
							html += "<br>";
							
						} else {
							
							html += "&emsp;&emsp;" + param.getType().getSimpleName() + " : ";
							try {
								
								ObjectMapper mapper = new ObjectMapper();
								Object obj = param.getType().newInstance();
								String jsonInString = mapper.writeValueAsString(obj);
								html += jsonInString;
							} catch (Exception e) {
							}
							html += "<br>";
							
						}
					}
				}
				
				html += "&emsp;Response:<br>";
				
				if (method.getGenericReturnType()!=null && method.getGenericReturnType().getTypeName().equals("Model")) {
					html += "&emsp;&emsp;" + this.getClasse().getSimpleName() + " : ";
					try {
						
						ObjectMapper mapper = new ObjectMapper();
						Object obj = this.getClasse().newInstance();
						String jsonInString = mapper.writeValueAsString(obj);
						html += jsonInString;
					} catch (Exception e) {
					}
				} else {
					html += "&emsp;&emsp;"+method.getReturnType().getSimpleName();
					if (method.getReturnType().equals(FormGet.class)) {
						html += "&lt;"+this.getClasse().getSimpleName()+"&gt;";
					}
					
					try {
						
						ObjectMapper mapper = new ObjectMapper();
						Object obj = method.getReturnType().newInstance();
						String jsonInString = mapper.writeValueAsString(obj);
						html += " :"+jsonInString;
					} catch (Exception e) {
					}
					
				}
				
				html += "<br>";
				html += "<br>";
				
			}
				
		}
		
		return "<div>" + html + "</div>";
	}
	
	@GET
	public FormGet<Model> get(@QueryParam("page") Integer page, @QueryParam("numberRecords") Integer numberRecords) throws RestException {
		
		try {
			
			if (page==null || page<=0) {
				page = 1;
			}
			
			if (numberRecords==null || numberRecords<=0) {
				numberRecords = 20;
			}
			
			FormGet<Model> form = new FormGet<>();
			String name = this.getClasse().getSimpleName();
			
			String jpql = "SELECT count(m) FROM "+name+" m";
			Query q = this.getEm().createQuery(jpql);
			Long totalRecords = (Long) q.getSingleResult();
			form.setTotalRecords(totalRecords);
			
			Long lastPage = totalRecords/numberRecords;
			if (totalRecords%numberRecords!=0) {
				lastPage++;
			}
			form.setLastPage(lastPage);
			form.setNumberRecords(numberRecords);
			
			this.sql = "from "+name + " m ";
			
			if (this.getFilters()!=null && this.getFilters().size()>0) {
				
				sql += " where ";
				
				this.getFilters().forEach(filtro -> {
					this.sql += ("m."+filtro.getName()+" = :"+filtro.getName());
				});
				
			}
			
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
			
			this.beforePost(model);
			this.getEm().persist(model);
			this.getSessao().commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		}
		
		return this.afterPost(model);
		
	}
	
	@PUT
	@Path("{id}")
	public Model put(@PathParam("id") Long id, Model model) throws RestException {
		
		try {
			
			this.setId(model, id);
			this.beforePut(model);
			this.getEm().merge(model);
			this.getSessao().commit();
			
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
