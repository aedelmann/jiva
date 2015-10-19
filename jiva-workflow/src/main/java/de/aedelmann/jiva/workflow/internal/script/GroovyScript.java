package de.aedelmann.jiva.workflow.internal.script;

import org.codehaus.groovy.control.CompilerConfiguration;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;
import de.aedelmann.jiva.workflow.script.Script;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyScript implements Script<Object> {

    static final String LANG_KEY = "groovy";
	
	private String value;
	
	private GroovyShell shell = null;
	private Binding binding = null;
	
	public GroovyScript(String value) {
		this.value = value;
		binding = new Binding();
        CompilerConfiguration cc = new CompilerConfiguration();
        shell = new GroovyShell(Thread.currentThread().getContextClassLoader(), binding, cc);
	}
	 
	@Override
	public String getLanguage() {
		return LANG_KEY;
	}

	@Override
	public Object evaluate(WorkflowContext ctx) {
		binding.setVariable("execution", ctx.getExecution());
		binding.setVariable("model", ctx.getModel());
        try {
            return shell.evaluate(value);
        } catch (Exception ex) {
        	throw new RuntimeException(ex);
        }
	}

}
