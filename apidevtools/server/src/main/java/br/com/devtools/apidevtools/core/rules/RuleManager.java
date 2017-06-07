package br.com.devtools.apidevtools.core.rules;

import java.util.List;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.searchclass.SearchClass;

public class RuleManager<Model> {

	private Controller<Model> controller;
	private String classPath;
	
	public RuleManager(Controller<Model> controller) {
		this.controller = controller;
		this.classPath = this.controller.getClass().getPackage().toString()+".rules";
		
		try {
			List<RulePost> list = new SearchClass(this.classPath).byInterface(RulePost.class);
			if (list!=null) {
				for (RulePost class1 : list) {
					this.controller.postRules.add(class1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
