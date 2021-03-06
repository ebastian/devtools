package br.com.devtools.apidevtools.resource.help;

import static br.com.devtools.apidevtools.core.permission.PermissionMethod.ALL;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.devtools.apidevtools.core.permission.PermissionClass;
import br.com.devtools.apidevtools.core.permission.PermissionMethod;
import br.com.devtools.apidevtools.core.searchclass.SearchClass;
import br.com.devtools.apidevtools.resource.user.permission.PermissionBuild;
import io.swagger.annotations.Api;

@Api("Ajuda")
@Path("help")
@Produces(MediaType.TEXT_HTML)
@PermissionClass(description="Ajuda")
public class HelpController {

	public static final String baseUrl = "http://localhost:8080/apidevtools/api/";
	
	public HelpController() {
	}
	
	@GET
	@PermissionMethod(types=ALL, description="Ajuda")
	public String help() {
		
		String help = "";
		
		try {
			
			SearchClass searchClass = new SearchClass("br.com.devtools.apidevtools.resource");
			List<Class<?>> controllers = searchClass.byAnnotation(Path.class);
			for (Class<?> classe : controllers) {
				if (!classe.equals(this.getClass())) {
					Path path = classe.getAnnotation(Path.class);
					help += "<a href=\"" + baseUrl + replaceParam(path.value()) + "/help\">"+classe.getSimpleName()+"</a><br>";
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		help += "<a href=\"" + baseUrl + replaceParam("help/privilege") + "\">Sistem Privilege</a><br>";
		help += "<a href=\"http://localhost:8080/apidevtools/doc/#/\">Swagger Help</a><br>";
		
		return "<div>"+help+"</div>";
	}
	
	@GET
	@Path("privilege")
	@Produces("application/json;charset=UTF-8")
	@PermissionMethod(types=ALL, description="Privilégios do Sistema")
	public Object privilege() throws Exception {
		return new PermissionBuild().getPermission();
	}
	
	public static String replaceParam(String path) {
		
		while (path.indexOf("{")>=0) {
			int ini = path.indexOf("{");
			int fin = path.indexOf("}");
			path = path.substring(0, ini) + "0" + path.substring(fin+1, path.length());
		}
		return path;
		
	}
	
}
