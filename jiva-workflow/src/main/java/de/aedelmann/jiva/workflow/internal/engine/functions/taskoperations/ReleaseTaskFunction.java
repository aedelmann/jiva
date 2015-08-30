package de.aedelmann.jiva.workflow.internal.engine.functions.taskoperations;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Alexander Edelmann
 */
@Component("ReleaseTaskFunction")
public class ReleaseTaskFunction implements FunctionProvider {


    @Override
    public void execute(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {

    }
}
