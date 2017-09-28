package br.com.devtools.apidevtools.core.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) 
public @interface PermissionClass {

	String description();
	String allMethods() default "";
	
}
