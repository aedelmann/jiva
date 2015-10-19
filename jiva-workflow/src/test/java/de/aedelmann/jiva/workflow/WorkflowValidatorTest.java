package de.aedelmann.jiva.workflow;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowValidator.InvalidInputException;

public class WorkflowValidatorTest extends AbstractWorkflowTest {

	@Test (expected = InvalidInputException.class)
    public void testStartWorkflowWithValidator() throws Exception {
    	registerModel("sample.jwl");
    	Map<String, Object> vars = new HashMap<>();
    	vars.put("validate", false);
    	workflowService.startWorkflow("approval", vars);
    }
    
    @Test
    public void testStartWorkflowWithValidator2() throws Exception {
    	registerModel("sample.jwl");
    	Map<String, Object> vars = new HashMap<>();
    	vars.put("validate", true);
    	assertNotNull(workflowService.startWorkflow("approval", vars));
    }
}
