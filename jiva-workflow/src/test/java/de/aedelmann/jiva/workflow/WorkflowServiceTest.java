package de.aedelmann.jiva.workflow;

import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.runtime.WorkflowInstance;
import de.aedelmann.jiva.workflow.runtime.WorkflowNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationcontext.xml"})
public class WorkflowServiceTest extends WorkflowTest {

    @Test (expected = WorkflowNotFoundException.class)
    public void testStartUndeployedWorkflow() {
        workflowService.startWorkflow("dummyId");
    }

    @Test
    public void testStartWorkflow() {
        Deployment deployment = deployWorkflow("sample.jwl");
        WorkflowInstance workflowInstance = workflowService.startWorkflow(deployment.getId());
        assertNotNull(workflowInstance);

    }
}
