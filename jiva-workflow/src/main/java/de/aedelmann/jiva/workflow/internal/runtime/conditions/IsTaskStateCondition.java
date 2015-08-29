package de.aedelmann.jiva.workflow.internal.runtime.conditions;


import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;

import java.util.Map;

/**
 * @author Alexander Edelmann
 */
public class IsTaskStateCondition implements Condition {



    @Override
    public boolean passesCondition(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {


        return false;
    }
}
