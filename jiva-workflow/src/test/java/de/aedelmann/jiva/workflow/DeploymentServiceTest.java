package de.aedelmann.jiva.workflow;

import com.opensymphony.workflow.loader.WorkflowDescriptor;
import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.deployment.MutableDeployment;
import de.aedelmann.jiva.workflow.jwl.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationcontext.xml"})
public class DeploymentServiceTest {

    @Autowired
    private DeploymentService deploymentService;

    @Test
    public void testCreateValidDeployment() throws IOException {
        MutableDeployment deployment = deploymentService.createDeployment();
        deployment.read(new ClassPathResource("sample.jwl").getInputStream());
        assertEquals("approval",deployment.getWorkflowModel().getId());
        assertNotNull(deployment.getWorkflowModel().getNodeById("managerApproval",Task.class));
    }

    @Test
    public void testDeploy() throws IOException {
        MutableDeployment deployment = deploymentService.createDeployment();
        deployment.read(new ClassPathResource("sample.jwl").getInputStream());

        assertNull(deployment.getWorkflowModel().getRuntimeModel(WorkflowDescriptor.class));
        deploymentService.deploy(deployment);

        Deployment activeDeployment = deploymentService.getDeployment(deployment.getId());
        assertNotNull(activeDeployment);
        WorkflowDescriptor workflowDescriptor = activeDeployment.getWorkflowModel().getRuntimeModel(WorkflowDescriptor.class);
        assertNotNull(workflowDescriptor);
        System.out.println(workflowDescriptor.asXML());
    }


}
