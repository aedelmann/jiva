package de.aedelmann.jiva.workflow;

import com.opensymphony.workflow.loader.WorkflowDescriptor;
import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.jwl.Transition;
import de.aedelmann.jiva.workflow.model.TaskAction;
import de.aedelmann.jiva.workflow.model.TaskState;
import de.aedelmann.jiva.workflow.model.WorkflowEnvironment;
import de.aedelmann.jiva.workflow.model.WorkflowInstance;
import de.aedelmann.jiva.workflow.model.WorkflowNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationcontext.xml"})
public class WorkflowServiceTest extends WorkflowTest {

    @Before
    public void setupTest() {
        WorkflowEnvironment.setCurrentUserId("alex");
    }

    @Test (expected = WorkflowNotFoundException.class)
    public void testStartUndeployedWorkflow() {
        workflowService.startWorkflow("dummyId");
    }

    @Test
    public void testStartWorkflow() {
        Deployment deployment = deployWorkflow("sample.jwl");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        assertNotNull(workflowInstance);
        assertNotNull(workflowInstance.getCreatedOn());
        assertEquals("alex",workflowInstance.getInitiator());
        assertTrue(Long.parseLong(workflowInstance.getId()) > 0);
        assertFalse(workflowInstance.isCompleted());
        assertNotNull(workflowInstance.getModel());
    }

    @Test
    public void testGetAssigneesOfWorkflow() {
        Deployment deployment = deployWorkflow("sample.jwl");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        assertEquals(2, workflowInstance.getPotentialOwners().size());
        assertTrue(workflowInstance.getPotentialOwners().contains("alex"));
        assertTrue(workflowInstance.getPotentialOwners().contains("jugal"));
    }

    @Test
    public void testGetWorkflowById() {
        Deployment deployment = deployWorkflow("sample.jwl");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        assertEquals(workflowInstance,workflowService.getWorkflow(workflowInstance.getId()));
    }

    @Test
    public void testGetAvailableTransitionsOfNonPotentialOwner() {
        Deployment deployment = deployWorkflow("sample.jwl");

        WorkflowEnvironment.setCurrentUserId("max");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        assertEquals(0, workflowService.getAvailableTransitions(workflowInstance.getId()).size());
    }

    @Test
    public void testGetAvailableTransitionsOfUnclaimedWorkflowPotentialOwner() {
        Deployment deployment = deployWorkflow("sample.jwl");
        WorkflowEnvironment.setCurrentUserId("jugal");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        assertEquals(1, workflowService.getAvailableTransitions(workflowInstance.getId()).size());
        assertEquals(TaskAction.Claim.getId(),workflowService.getAvailableTransitions(workflowInstance.getId()).toArray()[0]);
    }

    @Test
    public void testClaimWorkflowInstance() {
        Deployment deployment = deployWorkflow("sample.jwl");
        WorkflowEnvironment.setCurrentUserId("jugal");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        String claimTransition = (String)workflowService.getAvailableTransitions(workflowInstance.getId()).toArray()[0];
        workflowInstance = workflowService.takeTransition(workflowInstance.getId(),claimTransition);
        assertEquals(TaskState.CLAIMED,workflowInstance.getTaskState());
        assertEquals("jugal",workflowInstance.getActualOwner());
    }

    @Test
    public void testReleaseWorkflowInstance() {
        Deployment deployment = deployWorkflow("sample.jwl");
        WorkflowEnvironment.setCurrentUserId("jugal");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        workflowInstance = workflowService.takeTransition(workflowInstance.getId(),TaskAction.Claim.getId());
        assertEquals(TaskState.CLAIMED,workflowInstance.getTaskState());

        assertTrue(workflowService.getAvailableTransitions(workflowInstance.getId()).contains(TaskAction.Release.getId()));

        workflowInstance = workflowService.takeTransition(workflowInstance.getId(),TaskAction.Release.getId());
        assertEquals(TaskState.READY,workflowInstance.getTaskState());
        assertNull(workflowInstance.getActualOwner());
    }

}
