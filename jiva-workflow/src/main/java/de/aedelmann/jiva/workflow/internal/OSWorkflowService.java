package de.aedelmann.jiva.workflow.internal;

import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.Step;
import de.aedelmann.jiva.workflow.DeploymentService;
import de.aedelmann.jiva.workflow.WorkflowService;
import de.aedelmann.jiva.workflow.deployment.Deployment;
import de.aedelmann.jiva.workflow.internal.dao.WorkflowInstanceRepository;
import de.aedelmann.jiva.workflow.internal.model.OSWorkflowInstance;
import de.aedelmann.jiva.workflow.jwl.Task;
import de.aedelmann.jiva.workflow.jwl.Transition;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;
import de.aedelmann.jiva.workflow.model.Attachment;
import de.aedelmann.jiva.workflow.model.Comment;
import de.aedelmann.jiva.workflow.model.WorkflowEnvironment;
import de.aedelmann.jiva.workflow.model.WorkflowInstance;
import de.aedelmann.jiva.workflow.model.WorkflowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
/**
 * @author Alexander Edelmann
 */
@Transactional
public class OSWorkflowService implements WorkflowService {

    @Autowired
    private DeploymentService deploymentService;

    @Autowired
    private WorkflowInstanceRepository dao;

    @Autowired
    private Workflow workflow;

    @Override
    public WorkflowInstance startWorkflow(String deploymentId) {
        Deployment deployment = deploymentService.getDeployment(deploymentId);
        if (deployment == null) {
            throw new WorkflowNotFoundException();
        }

        Map<String, Object> vars = new HashMap<String, Object>();


        OSWorkflowInstance workflowInstance = new OSWorkflowInstance(workflow, WorkflowEnvironment.current().getCurrentUserId(),deployment.getWorkflowModel());
        vars.put("jiva.workflow",workflowInstance);
        try {
            long id = workflow.initialize(deploymentId, 1, vars);
            workflowInstance.setWorkflowInstanceId(id);
        } catch (WorkflowException e) {
            throw new RuntimeException(e);
        }

        workflowInstance = dao.save(workflowInstance);

        return workflowInstance;
    }

    @Override
    public List<Transition> getAvailableTransitions(String workflowInstanceId) {
        OSWorkflowInstance workflowInstance = (OSWorkflowInstance)getWorkflow(workflowInstanceId);

        List<Transition> transitions = new ArrayList<>();
        WorkflowDescriptor descriptor = workflowInstance.getWorkflowDescriptor();
        Map<String,Object> vars = new HashMap<>();
        vars.put("jiva.workflow",workflowInstance);
        int[] actionIds = workflow.getAvailableActions(workflowInstance.getOSWorkflowId(), vars);
        Step currentStep =  (Step)workflow.getCurrentSteps(workflowInstance.getOSWorkflowId()).get(0);

        WorkflowModel workflowModel = workflowInstance.getModel();
        Task currentTaskModel = workflowModel.getNodeById(descriptor.getStep(currentStep.getStepId()).getName(), Task.class);
        for (int actionId : actionIds) {
            ActionDescriptor ad = descriptor.getAction(actionId);
            transitions.add(currentTaskModel.getTransitionByName(ad.getName()));

        }
        return transitions;
    }

    @Override
    public WorkflowInstance takeTransition(String workflowInstanceId, String transitionId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public WorkflowInstance getWorkflow(String workflowInstanceId) {
        OSWorkflowInstance workflowInstance =  dao.findOne(Long.parseLong(workflowInstanceId));
        Deployment deployment = deploymentService.getDeployment(workflow.getWorkflowName(workflowInstance.getOSWorkflowId()));
        workflowInstance.setWorkflowModel(deployment.getWorkflowModel());
        return workflowInstance;
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
