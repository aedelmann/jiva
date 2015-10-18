package de.aedelmann.jiva.workflow.internal.engine.extensions;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowModelExtension;
import de.aedelmann.jiva.workflow.internal.script.ScriptFactory;
import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;

public abstract class ScriptExecutor implements WorkflowModelExtension {

	private String lang;
	private String expression;
	
	@Override
	public void validate() throws ModelValidationProblem {
		// do some validation here
	}

	protected Object executeScript(WorkflowContext context) {
		return ScriptFactory.getInstance().getScript(lang, expression).evaluate(context);
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}
