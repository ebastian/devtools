package br.com.devtools.apidevtools.core.help;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.devtools.apidevtools.core.controller.FormGet;
import br.com.devtools.apidevtools.resource.help.HelpController;

public class HelpGenerator {

	public String help(Class<?> classe, Class<?> classModel) {
		
		String html = "";
		html += "-- " + classe.getSimpleName() + " --<br><br>";
		html += "<a href=\"http://localhost:8080/apidevtools/api/help\">Voltar</a><br><br>";
		
		Path annotation = classe.getAnnotation(Path.class);
		
		String url = HelpController.baseUrl + annotation.value();
		
		html += url + "<br><br>";
		
		
		Method[] methods = classe.getMethods();
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
						} else if (classModel!=null && param.getParameterizedType()!=null && param.getParameterizedType().getTypeName().equals("Model")) {
							
							html += "&emsp;&emsp;" + classModel.getSimpleName() + " : ";
							try {
								
								ObjectMapper mapper = new ObjectMapper();
								Object obj = classModel.newInstance();
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
				
				if (classModel!=null && method.getGenericReturnType()!=null && method.getGenericReturnType().getTypeName().equals("Model")) {
					html += "&emsp;&emsp;" + classModel.getSimpleName() + " : ";
					try {
						
						ObjectMapper mapper = new ObjectMapper();
						Object obj = classModel.newInstance();
						String jsonInString = mapper.writeValueAsString(obj);
						html += jsonInString;
					} catch (Exception e) {
					}
				} else {
					html += "&emsp;&emsp;"+method.getReturnType().getSimpleName();
					if (method.getReturnType().equals(FormGet.class)) {
						html += "&lt;"+classModel.getSimpleName()+"&gt;";
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
	
	
}
