package de.aedelmann.jiva.workflow;

import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.deployment.MutableDeployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Transactional
public class WorkflowTest {

    @Autowired
    protected DeploymentService deploymentService;

    @Autowired
    protected WorkflowService workflowService;

    protected Deployment deployWorkflow(String workflowName) {
        MutableDeployment deployment = deploymentService.createDeployment();
        try {
            deployment.read(new ClassPathResource(workflowName).getInputStream());
            deploymentService.deploy(deployment);
            return deployment;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
