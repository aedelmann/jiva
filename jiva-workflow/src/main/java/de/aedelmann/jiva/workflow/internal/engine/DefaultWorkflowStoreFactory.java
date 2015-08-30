package de.aedelmann.jiva.workflow.internal.engine;

import com.opensymphony.workflow.FactoryException;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.loader.WorkflowFactory;
import de.aedelmann.jiva.workflow.DeploymentService;
import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Properties;

/**
 * @author Alexander Edelmann
 */
public class DefaultWorkflowStoreFactory implements WorkflowFactory {

    @Autowired
    private DeploymentService deploymentService = null;

    public WorkflowDescriptor getWorkflow(String name) throws FactoryException {
        WorkflowModel model = deploymentService.getDeployment(name).getWorkflowModel();
        return model.getRuntimeModel(WorkflowDescriptor.class);
    }

    public String[] getWorkflowNames() throws FactoryException {
        List<Deployment> allDeployments = this.deploymentService.listDeployments();

        String[] result = new String[allDeployments.size()];
        int counter = 0;

        for (Deployment d : allDeployments) {
            result[counter++] = d.getId();
        }

        return result;
    }

    public void createWorkflow(String arg0) {
    }

    public Object getLayout(String arg0) {
        return null;
    }

    public String getName() {
        return null;
    }

    public Properties getProperties() {
        return null;
    }

    public WorkflowDescriptor getWorkflow(String workflowName, boolean validate)
            throws FactoryException {
        return getWorkflow(workflowName);
    }

    public void init(Properties properties) {
        ;
    }

    public void initDone() throws FactoryException {
        ;
    }

    public boolean isModifiable(String arg0) {
        return false;
    }

    public boolean removeWorkflow(String arg0) throws FactoryException {
        return false;
    }

    public void renameWorkflow(String arg0, String arg1) {
        ;
    }

    public void save() {
        ;
    }

    public boolean saveWorkflow(String arg0, WorkflowDescriptor arg1, boolean arg2)
            throws FactoryException {
        return false;
    }

    public void setLayout(String arg0, Object arg1) {
        ;
    }
}
