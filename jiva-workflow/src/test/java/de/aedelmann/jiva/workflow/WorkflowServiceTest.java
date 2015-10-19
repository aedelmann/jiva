package de.aedelmann.jiva.workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.aedelmann.jiva.workflow.engine.WorkflowExecution;
import de.aedelmann.jiva.workflow.engine.WorkflowState;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowValidator.InvalidInputException;


public class WorkflowServiceTest extends AbstractWorkflowTest {

    @Test
    public void testStartWorkflow()  {
    	registerModel("sample.jwl");
    	WorkflowExecution startedWorkflow =  workflowService.startWorkflow("approval", Collections.emptyMap());
    	assertNotNull(startedWorkflow.getId());
    	assertNotNull(startedWorkflow.getCreatedOn());
    	assertEquals("alex",startedWorkflow.getInitiator());
    	assertEquals("approval",startedWorkflow.getName());
    	assertEquals(WorkflowState.ACTIVE,startedWorkflow.getState());
    }
    
    @Test
    public void testGetCurrentStepOfStartedWorkflow()  {
    	registerModel("sample.jwl");
    	WorkflowExecution startedWorkflow =  workflowService.startWorkflow("approval", Collections.emptyMap());
    	assertEquals(1,startedWorkflow.getActiveExecutions().size());
    	assertEquals("managerApproval",startedWorkflow.getActiveExecutions().get(0).getName());
    }

    @Test
    public void testFindWorkflowByInstanceId() {
    	registerModel("sample.jwl");
    	WorkflowExecution startedWorkflow =  workflowService.startWorkflow("approval", Collections.emptyMap());
    	assertNotNull(workflowService.getWorkflow(startedWorkflow.getId()));
    }
    
    @Test
    public void testTakeTransition() {
    	registerModel("sample.jwl");
    	WorkflowExecution workflowInstance =  workflowService.startWorkflow("approval", Collections.emptyMap());
    	workflowInstance = workflowService.takeTransition(workflowInstance.getId(), "Approve", Collections.emptyMap());
    	assertTrue(workflowInstance.isCompleted());
    	assertNotNull(workflowInstance.getCompletedOn());
    	assertEquals(WorkflowState.ENDED,workflowInstance.getState());
    	assertEquals(0,workflowInstance.getActiveExecutions().size());
    	assertEquals(2,workflowInstance.getCompletedExecutions().size());
    }
    
    @Test
    public void testGetAvailableTransitions() {
    	registerModel("sample.jwl");
    	WorkflowExecution workflowInstance =  workflowService.startWorkflow("approval", Collections.emptyMap());
    	assertEquals(2,workflowService.getAvailableTransitions(workflowInstance.getId(), Collections.emptyMap()).size());
    }

}
