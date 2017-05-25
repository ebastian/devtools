package br.com.devtools.apidevtools.core.searchclass;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.devtools.apidevtools.App;

public class SearchClass {

	private String packagePath;
	private List<Class<?>> classes = new ArrayList<>();
	private String prefix = "/WEB-INF/classes/";
	
	public SearchClass(String packageName) {
		
		String path = packageName.replace(".", "/");
		this.packagePath = prefix + path;
		
	}
	
	private void findAnnotation(Class<? extends Annotation> annotation, String path) throws Exception {
		
		@SuppressWarnings("unchecked")
		Set<String> resourcePaths = App.context.getResourcePaths(path);
		
		for (String arquivo : resourcePaths) {
			if (arquivo.lastIndexOf(".class") >= 0) {
				
				String className = arquivo.substring(prefix.length()).replace("/", ".");
				className = className.substring(0, className.length()-6);
				
				Class<?> forName = Class.forName(className);
				if (forName.isAnnotationPresent(annotation)) {
					classes.add(forName);
				}
				
			} else {
				
				this.findAnnotation(annotation, arquivo);
				
			}
		}
		
	}
	
	public List<Class<?>> byAnnotation(Class<? extends Annotation> annotation) throws Exception {
		
		this.findAnnotation(annotation, this.packagePath);
		return classes;
		
	}
	
	/*
	public List<Class<?>> getClasses() throws Exception {

		try {

			List<Class<?>> classes = new ArrayList<>();

			String path = this.packageName.replace(".", "/");
			String prefix = "/WEB-INF/classes/";
			
			@SuppressWarnings("unchecked")
			Set<String> resourcePaths = App.context.getResourcePaths(prefix + path);

			for (String arquivo : resourcePaths) {
				if (arquivo.lastIndexOf(".class") >= 0) {

					int ini = arquivo.lastIndexOf("/") + 1;
					int fim = arquivo.lastIndexOf(".");

					String nome = arquivo.substring(ini, fim);
					Class<?> forName = Class.forName(this.packageName + "." + nome);

					classes.add(forName);

				}
			}

			return classes;

		} catch (Exception e) {
			throw e;
		}

	}
	*/
}
