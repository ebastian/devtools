package br.com.devtools.apidevtools.resource.componentlast;

import br.com.devtools.apidevtools.resource.component.Component;
import br.com.devtools.apidevtools.resource.component.version.Version;
import br.com.devtools.apidevtools.resource.component.version.build.Build;

public class ComponentLast {

	private Component component;
	private Version version;
	private Build build;
	
	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	public Version getVersion() {
		return version;
	}
	public void setVersion(Version version) {
		this.version = version;
	}
	public Build getBuild() {
		return build;
	}
	public void setBuild(Build build) {
		this.build = build;
	}
	
}
