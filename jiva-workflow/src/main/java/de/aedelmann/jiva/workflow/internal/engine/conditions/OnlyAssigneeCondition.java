package de.aedelmann.jiva.workflow.internal.engine.conditions;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;
import de.aedelmann.jiva.workflow.internal.model.OSWorkflowInstance;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Alexander Edelmann
 */
@Component("OnlyAssigneeCondition")
public class OnlyAssigneeCondition implements Condition {
    @Override
    public boolean passesCondition(Map transientVars, Map map2, PropertySet propertySet) throws WorkflowException {
        OSWorkflowInstance workflowInstance = (OSWorkflowInstance)transientVars.get("jiva.workflow");
        return workflowInstance.getPotentialOwners().contains(workflowInstance.getInitiator());
    }
}
