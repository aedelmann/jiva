package de.aedelmann.jiva.workflow.internal;

import de.aedelmann.jiva.workflow.DeploymentService;
import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.deployment.MutableDeployment;
import de.aedelmann.jiva.workflow.internal.deployment.DefaultDeployment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
/**
 * @author Alexander Edelmann
 */
public class DefaultDeploymentService implements DeploymentService {

    @Override
    public MutableDeployment createDeployment() {
        return new DefaultDeployment();
    }

    @Override
    public void deploy(MutableDeployment deployment) {

    }

    @Override
    public Deployment getDeployment(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Deployment> listDeployments() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
