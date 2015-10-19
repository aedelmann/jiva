package de.aedelmann.jiva.workflow.internal.script;

import de.aedelmann.jiva.workflow.script.Script;

public class ScriptFactory {

	private static ScriptFactory instance = null;
	
	private ScriptFactory() {	
	}
	
	public static ScriptFactory getInstance() {
		if (instance == null) {
			instance = new ScriptFactory();
		}
		
		return instance;
	}
	
	public Script<Object> getScript(String language, String expression) {
		if (language.equalsIgnoreCase(GroovyScript.LANG_KEY)) {
			return new GroovyScript(expression);
		}
		return null;
	}
	
}
