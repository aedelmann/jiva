package de.aedelmann.jiva.workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.aedelmann.jiva.workflow.engine.WorkflowExecution;

public class WorkflowConditionTest extends AbstractWorkflowTest {

	@Test
	public void testGetAvailableTransitions() {
		registerModel("sample-conditions.jwl");
		
		 WorkflowExecution execution = workflowService.startWorkflow("approval", Collections.emptyMap());
		 Map<String, Object> vars = new HashMap<>();
		 vars.put("owner", "stefan");
		 assertEquals(1,workflowService.getAvailableTransitions(execution.getId(), vars).size());
		 assertTrue(workflowService.getAvailableTransitions(execution.getId(), vars).contains("Reject"));
	}
	
	@Test
	public void testGetAvailableTransitions2() {
		registerModel("sample-conditions.jwl");
		
		 WorkflowExecution execution = workflowService.startWorkflow("approval", Collections.emptyMap());
		 Map<String, Object> vars = new HashMap<>();
		 vars.put("owner", "alex");
		 assertEquals(2,workflowService.getAvailableTransitions(execution.getId(), vars).size());
		 assertTrue(workflowService.getAvailableTransitions(execution.getId(), vars).contains("Approve"));
		 assertTrue(workflowService.getAvailableTransitions(execution.getId(), vars).contains("Reject"));
	}
}
