package de.aedelmann.jiva.workflow;

import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.deployment.MutableDeployment;

import java.util.List;

/**
 * The Deployment Service publishes workflow applications so they can be controlled
 * using the {@link WorkflowService}
 *
 * @author Alexander Edelmann
 */
public interface DeploymentService {

    /**
     * Creates a new deployment where the user can specify the content of this deployment.
     * After that the deployment can actually be deployed with {@link DeploymentService#deploy(de.aedelmann.jiva.workflow.deployment.MutableDeployment)}
     *
     * @return
     */
    MutableDeployment createDeployment();

    /**
     * Deploys the deployable artifacts to the engine
     *
     * @post deployment is persisted and is ready for new workflow instances {@See WorkflowService}
     *
     * @param deployment
     */
    void deploy(MutableDeployment deployment);

    /**
     * Gets a deployed workflow application by the specified id
     * @param id
     *
     * @return deployment that was found by the specified id, can be null
     */
    Deployment getDeployment(String id);

    /**
     * Gets a list of all deployments that are currently available to the engine
     * @return
     */
    List<Deployment> listDeployments();
}
