package br.com.devtools.apidevtools.core.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) 
public @interface PermissionMethod {

	public enum Type {
	   NONE, ALL, GET, POST, PUT, DELETE, AUDIT
	}
	
	Type[] types() default Type.NONE;
	
	String[] custom() default "";
	
	String description();
	
}
