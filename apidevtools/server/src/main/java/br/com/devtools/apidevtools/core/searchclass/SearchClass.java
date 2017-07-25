package br.com.devtools.apidevtools.core.searchclass;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.devtools.apidevtools.App;

public class SearchClass {

	private String packagePath;
	private List<Class<?>> classes = new ArrayList<>();
	private String prefix = "/WEB-INF/classes/";
	
	public SearchClass(String packageName) {
		
		if (App.context==null) {
			this.prefix = this.getClass().getClassLoader().getResource("").getPath().toString().replace("test-classes", "classes");
			if (this.prefix.indexOf("/")==0) {
				this.prefix = this.prefix.substring(1);
			}
		}
		
		String path = packageName.replace(".", "/");
		this.packagePath = prefix + path;
		
	}
	
	@SuppressWarnings("unchecked")
	private void findAnnotation(Class<? extends Annotation> annotation, String path) throws Exception {
		
		Set<String> resourcePaths = null;
		
		if (App.context!=null) {
			resourcePaths = App.context.getResourcePaths(path);
		} else {
			File directory = new File(path);
			resourcePaths = new HashSet<>();
			for (File file : directory.listFiles()) {
				resourcePaths.add(file.getPath());
			}
		}
		
		for (String arquivo : resourcePaths) {
			if (arquivo.lastIndexOf(".class") >= 0) {
				
				String className = arquivo.substring(prefix.length()).replace("/", ".").replace("\\", ".");
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
	private <I> void findInterface(Class<I> interfacee, List<Class<I>> listClass, String path) throws Exception {
		
		Set<String> resourcePaths = null;
		
		if (App.context!=null) {
			resourcePaths = App.context.getResourcePaths(path);
		} else {
			File directory = new File(path);
			resourcePaths = new HashSet<>();
			File[] listFiles = directory.listFiles();
			if (listFiles!=null) {
				for (File file : directory.listFiles()) {
					resourcePaths.add(file.getPath());
				}
			}
		}
		
		if (resourcePaths==null) {
			return;
		}
		for (String arquivo : resourcePaths) {
			if (arquivo.lastIndexOf(".class") >= 0) {
				
				String className = arquivo.substring(prefix.length()).replace("/", ".").replace("\\", ".");
				className = className.substring(0, className.length()-6);
				
				Class<?> forName = Class.forName(className);
				if (Arrays.asList(forName.getInterfaces()).contains(interfacee)) {
					listClass.add((Class<I>)forName);
				}
				
			} else {
				
				this.findInterface(interfacee, listClass, arquivo);
				
			}
		}
		
	}
	
	public <I> List<Class<I>> byInterface(Class<I> interfacee) throws Exception {
		
		List<Class<I>> listClass = new ArrayList<>();
		this.findInterface(interfacee, listClass, this.packagePath);
		return listClass;
		
	}
	
}
