package de.aedelmann.jiva.workflow.internal;

import de.aedelmann.jiva.workflow.DeploymentService;
import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.deployment.MutableDeployment;
import de.aedelmann.jiva.workflow.internal.deployment.DefaultDeployment;
import de.aedelmann.jiva.workflow.internal.jwl.JaxbWorkflow;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.MappingContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
/**
 * @author Alexander Edelmann
 */
public class DeploymentServiceInMemory implements DeploymentService {

    private static Map<String,Deployment> deployments = new HashMap<String, Deployment>();

    @Override
    public MutableDeployment createDeployment() {
        return new DefaultDeployment();
    }

    @Override
    public void deploy(MutableDeployment deployment) {
        if (((DefaultDeployment)deployment).isRead()) {
            // generate runtime model
            JaxbWorkflow workflowModel = (JaxbWorkflow)deployment.getWorkflowModel();
            workflowModel.generateRuntimeModel(new MappingContext());
            deployments.put(deployment.getId(),deployment);
        }
    }

    @Override
    public Deployment getDeployment(String id) {
        return deployments.get(id);
    }

    @Override
    public List<Deployment> listDeployments() {
        return new ArrayList<Deployment>(deployments.values());
    }
}
