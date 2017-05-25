package br.com.devtools.apidevtools.resource.help;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.devtools.apidevtools.core.searchclass.SearchClass;

@Path("help")
@Produces(MediaType.TEXT_HTML)
public class HelpController {

	public static final String baseUrl = "http://localhost:8080/apidevtools/api/";
	
	public HelpController() {
		System.out.println("HelpController");
	}
	
	@GET
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
		
		return "<div>"+help+"</div>";
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
