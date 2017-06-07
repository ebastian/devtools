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
	

	@SuppressWarnings("unchecked")
	private <I> void findInterface(Class<I> interfacee, List<I> listClass, String path) throws Exception {
		
		Set<String> resourcePaths = App.context.getResourcePaths(path);
		
		for (String arquivo : resourcePaths) {
			if (arquivo.lastIndexOf(".class") >= 0) {
				
				String className = arquivo.substring(prefix.length()).replace("/", ".");
				className = className.substring(0, className.length()-6);
				
				Class<?> forName = Class.forName(className);
				if (forName.isInstance(interfacee)) {
					listClass.add((I)forName);
				}
				
			} else {
				
				this.findInterface(interfacee, listClass, arquivo);
				
			}
		}
		
	}
	
	public <I> List<I> byInterface(Class<I> interfacee) throws Exception {
		
		List<I> listClass = new ArrayList<>();
		this.findInterface(interfacee, listClass, this.packagePath);
		return listClass;
		
	}
	
}
