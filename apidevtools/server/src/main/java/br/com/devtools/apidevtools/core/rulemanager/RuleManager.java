package br.com.devtools.apidevtools.core.rulemanager;

import java.util.ArrayList;
import java.util.List;

import br.com.devtools.apidevtools.core.controller.Controller;
import br.com.devtools.apidevtools.core.rulemanager.rules.RuleDelete;
import br.com.devtools.apidevtools.core.rulemanager.rules.RuleGet;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePost;
import br.com.devtools.apidevtools.core.rulemanager.rules.RulePut;
import br.com.devtools.apidevtools.core.searchclass.SearchClass;

@SuppressWarnings("rawtypes")
public class RuleManager<Model> {

	private Controller<Model> controller;
	private String classPath;
	
	public RuleManager(Controller<Model> controller) {
		
		this.controller = controller;
		this.classPath = this.controller.getClass().getPackage().getName()+".rules";
		
	}
	

	public List<RulePost> postRules() throws Exception {

		try {
			List<Class<RulePost>> classes = new SearchClass(this.classPath).byInterface(RulePost.class);
			List<RulePost> list = new ArrayList<>();
			for (Class<RulePost> class1 : classes) {
				list.add(class1.newInstance());
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public List<RuleGet> getRules() throws Exception {

		try {
			List<Class<RuleGet>> classes = new SearchClass(this.classPath).byInterface(RuleGet.class);
			List<RuleGet> list = new ArrayList<>();
			for (Class<RuleGet> class1 : classes) {
				list.add(class1.newInstance());
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public List<RulePut> putRules() throws Exception {

		try {
			List<Class<RulePut>> classes = new SearchClass(this.classPath).byInterface(RulePut.class);
			List<RulePut> list = new ArrayList<>();
			for (Class<RulePut> class1 : classes) {
				list.add(class1.newInstance());
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public List<RuleDelete> deleteRules() throws Exception {

		try {
			List<Class<RuleDelete>> classes = new SearchClass(this.classPath).byInterface(RuleDelete.class);
			List<RuleDelete> list = new ArrayList<>();
			for (Class<RuleDelete> class1 : classes) {
				list.add(class1.newInstance());
			}
			return list;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
}
