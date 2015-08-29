package de.aedelmann.jiva.workflow.internal;

import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import de.aedelmann.jiva.workflow.DeploymentService;
import de.aedelmann.jiva.workflow.WorkflowService;
import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.internal.runtime.OSWorkflowInstance;
import de.aedelmann.jiva.workflow.jwl.Transition;
import de.aedelmann.jiva.workflow.runtime.Attachment;
import de.aedelmann.jiva.workflow.runtime.Comment;
import de.aedelmann.jiva.workflow.runtime.WorkflowInstance;
import de.aedelmann.jiva.workflow.runtime.WorkflowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
/**
 * @author Alexander Edelmann
 */
public class OSWorkflowService implements WorkflowService {

    @Autowired
    private DeploymentService deploymentService;

    @Autowired
    private Workflow workflow;

    @Override
    @Transactional
    public WorkflowInstance startWorkflow(String deploymentId) {
        Deployment deployment = deploymentService.getDeployment(deploymentId);
        if (deployment == null) {
            throw new WorkflowNotFoundException();
        }

        Map<String, Object> vars = new HashMap<String, Object>();

        long id = -1;

        try {
            id = workflow.initialize(deploymentId, 1, vars);
        } catch (WorkflowException e) {
            throw new RuntimeException(e);
        }

        return new OSWorkflowInstance(id,workflow,deployment.getWorkflowModel());
    }

    @Override
    public List<Transition> getAvailableTransitions(String workflowInstanceId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public WorkflowInstance takeTransition(String workflowInstanceId, String transitionId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public WorkflowInstance getWorkflow(String workflowInstanceId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Comment addComment(String workflowInstanceId, String comment) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Comment addCommentReply(String commentId, String message) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Attachment addAttachment(String workflowInstanceId, String name, String contentType, byte[] data) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Attachment> getAttachments(String workflowInstanceId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Comment> getComments(String workflowInstanceId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
