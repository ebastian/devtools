package br.com.devtools.apidevtools.core.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) 
public @interface PermissionMethod {

	public static final String ALL = "ALL";
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	public static final String AUDIT = "AUDIT";
	
	String[] types() default ALL;
	
	String description();
	
}
