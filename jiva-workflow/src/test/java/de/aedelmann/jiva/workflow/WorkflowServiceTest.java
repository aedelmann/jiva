package de.aedelmann.jiva.workflow;

import com.opensymphony.workflow.loader.WorkflowDescriptor;
import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.internal.jwl.JaxbWorkflow;
import de.aedelmann.jiva.workflow.internal.model.OSWorkflowInstance;
import de.aedelmann.jiva.workflow.model.WorkflowEnvironment;
import de.aedelmann.jiva.workflow.model.WorkflowInstance;
import de.aedelmann.jiva.workflow.model.WorkflowNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

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
        System.out.println(deployment.getWorkflowModel().getRuntimeModel(WorkflowDescriptor.class).asXML());

        WorkflowEnvironment.setCurrentUserId("max");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        assertEquals(0, workflowService.getAvailableTransitions(workflowInstance.getId()).size());
    }

    @Test
    public void testGetAvailableTransitionsOfUnclaimedWorkflowPotentialOwner() {
        Deployment deployment = deployWorkflow("sample.jwl");
        System.out.println(deployment.getWorkflowModel().getRuntimeModel(WorkflowDescriptor.class).asXML());

        WorkflowEnvironment.setCurrentUserId("jugal");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        assertEquals(1, workflowService.getAvailableTransitions(workflowInstance.getId()).size());
    }

}
