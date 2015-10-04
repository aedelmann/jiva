package de.aedelmann.jiva.workflow;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.aedelmann.jiva.workflow.engine.WorkflowEnvironment;
import de.aedelmann.jiva.workflow.engine.WorkflowExecution;
import de.aedelmann.jiva.workflow.engine.WorkflowState;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationcontext.xml"})
@Transactional
public class WorkflowServiceTest {

	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private WorkflowModelStore modelStore;
	
    @Before
    public void setupTest() {
        WorkflowEnvironment.setCurrentUserId("alex");
    }
    
    protected void registerModel(String workflowFile) {
    	try {
			modelStore.addModel(new ClassPathResource(workflowFile).getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
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

}
