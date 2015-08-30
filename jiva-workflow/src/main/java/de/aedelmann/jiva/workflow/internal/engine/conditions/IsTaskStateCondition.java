package de.aedelmann.jiva.workflow.internal.engine.conditions;


import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;
import de.aedelmann.jiva.workflow.internal.model.OSWorkflowInstance;
import de.aedelmann.jiva.workflow.model.TaskState;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Alexander Edelmann
 */
@Component("IsTaskStateCondition")
public class IsTaskStateCondition implements Condition {

    public static final String PARAM = "jiva.task.state";

    @Override
    public boolean passesCondition(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String state = (String)args.get(PARAM);
        OSWorkflowInstance workflowInstance = (OSWorkflowInstance)transientVars.get("jiva.workflow");
        return workflowInstance.getTaskState() == TaskState.valueOf(state);
    }
}
