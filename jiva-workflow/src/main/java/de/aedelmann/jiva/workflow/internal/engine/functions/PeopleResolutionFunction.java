package de.aedelmann.jiva.workflow.internal.engine.functions;


import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import de.aedelmann.jiva.workflow.WorkflowService;
import de.aedelmann.jiva.workflow.internal.dao.WorkflowInstanceRepository;
import de.aedelmann.jiva.workflow.internal.model.OSWorkflowInstance;
import de.aedelmann.jiva.workflow.model.TaskState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Alexander Edelmann
 */
@Component("PeopleResolutionFunction")
public class PeopleResolutionFunction implements FunctionProvider {

    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;

    @Override
    public void execute(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        OSWorkflowInstance workflowInstance = (OSWorkflowInstance) transientVars.get("jiva.workflow");
        if (workflowInstance.getTaskState() == TaskState.READY) {
            return;
        }
        workflowInstance.setWorkflowInstanceId(((WorkflowEntry)transientVars.get("entry")).getId());
        workflowInstance.resolvePotentialOwners((Step) transientVars.get("createdStep"));
        workflowInstance.setTaskState(TaskState.READY);

        workflowInstanceRepository.save(workflowInstance);
    }
}
