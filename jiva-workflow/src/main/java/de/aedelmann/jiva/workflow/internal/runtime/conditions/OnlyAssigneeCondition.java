package de.aedelmann.jiva.workflow.internal.runtime.conditions;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;

import java.util.Map;

/**
 * @author Alexander Edelmann
 */
public class OnlyAssigneeCondition implements Condition {
    @Override
    public boolean passesCondition(Map map, Map map2, PropertySet propertySet) throws WorkflowException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
