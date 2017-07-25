package br.com.devtools.apidevtools.resource.component.version.build.condition;

public class TestBuildConditionFactory {

	private BuildCondition bc;
	
	public TestBuildConditionFactory() {
		bc = new BuildCondition();
	}
	
	public TestBuildConditionFactory createValid() {
		bc.setDescription("Teste ISSUE-1234");
		bc.setStatus(BuildConditionStatus.UNRATED);
		return this;
	}
	
	/*
	public TestBuildConditionFactory creationNull() {
		b.setCreation(null);
		return this;
	}
	
	public TestBuildConditionFactory creationGreaterNow() {
		b.setCreation(LocalDateTime.now().plusDays(1));
		return this;
	}
	
	public TestBuildConditionFactory buildNull() {
		b.setBuild(null);
		return this;
	}

	public TestBuildConditionFactory buildInvalid() {
		b.setBuild(-1);
		return this;
	}
	*/
	
	public BuildCondition get() {
		return this.bc;
	}
	
}
