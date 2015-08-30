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
@Component("IsAssignedCondition")
public class IsAssignedCondition implements Condition {

    @Override
    public boolean passesCondition(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
//        OSWorkflowInstance workflowInstance = (OSWorkflowInstance)transientVars.get("jiva.workflow");
//        String caller = (String)transientVars.get("caller");
        return false;
    }
}
