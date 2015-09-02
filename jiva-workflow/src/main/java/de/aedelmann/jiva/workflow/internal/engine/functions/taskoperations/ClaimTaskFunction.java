package de.aedelmann.jiva.workflow.internal.engine.functions.taskoperations;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import de.aedelmann.jiva.workflow.internal.dao.WorkflowInstanceRepository;
import de.aedelmann.jiva.workflow.internal.model.OSWorkflowInstance;
import de.aedelmann.jiva.workflow.model.TaskState;
import de.aedelmann.jiva.workflow.model.WorkflowEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Alexander Edelmann
 */
@Component("ClaimTaskFunction")
public class ClaimTaskFunction implements FunctionProvider {

    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;

    @Override
    public void execute(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        OSWorkflowInstance workflowInstance = (OSWorkflowInstance)transientVars.get("jiva.workflow");
        workflowInstance.claim(WorkflowEnvironment.current().getCurrentUserId());
        workflowInstanceRepository.save(workflowInstance);
    }
}

