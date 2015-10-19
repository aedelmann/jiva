package de.aedelmann.jiva.workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.aedelmann.jiva.workflow.engine.WorkflowExecution;

public class WorkflowActionTest extends AbstractWorkflowTest {

	@Test
	public void testComputeCalculation() {
		registerModel("sample-actions.jwl");
		WorkflowExecution execution = this.workflowService.startWorkflow("computer", Collections.emptyMap());
		assertTrue(this.workflowService.getAvailableTransitions(execution.getId()).contains("add"));
		assertTrue(this.workflowService.getAvailableTransitions(execution.getId()).contains("multiply"));
		Map<String, Object> vars = new HashMap<>();
		vars.put("num1", 50);
		vars.put("num2", 2);
		execution = workflowService.takeTransition(execution.getId(), "add", vars);
		assertEquals(52,execution.getVariables().get("result"));
		execution = workflowService.takeTransition(execution.getId(), "multiply", vars);
		assertEquals(100,execution.getVariables().get("result"));
	}
}
