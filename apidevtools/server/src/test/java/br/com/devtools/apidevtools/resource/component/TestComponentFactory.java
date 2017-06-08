package br.com.devtools.apidevtools.resource.component;

public class TestComponentFactory {
	
	private Component c;
	
	public TestComponentFactory() {
		c = new Component();
	}
	
	public TestComponentFactory createValid() {
		c.setName("Component Test");
		c.setDescription("Compnent Description Test");
		c.setFileName("ComponentTest.txt");
		return this;
	}
	
	public TestComponentFactory nameNull() {
		c.setName(null);
		return this;
	}
	
	public TestComponentFactory nameLess() {
		c.setName("a");
		return this;
	}
	
	public TestComponentFactory nameLager() {
		c.setName("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");
		return this;
	}
	
	
	public Component get() {
		return this.c;
	}
	
}
