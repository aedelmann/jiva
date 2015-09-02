package de.aedelmann.jiva.workflow.internal.jwl.task.actions;


import com.opensymphony.workflow.loader.ConditionDescriptor;
import com.opensymphony.workflow.loader.FunctionDescriptor;
import de.aedelmann.jiva.workflow.internal.engine.conditions.IsTaskStateCondition;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.OSWorkflowUtils;
import de.aedelmann.jiva.workflow.internal.engine.conditions.OnlyAssigneeCondition;
import de.aedelmann.jiva.workflow.internal.engine.functions.taskoperations.ReleaseTaskFunction;
import de.aedelmann.jiva.workflow.model.TaskAction;
import de.aedelmann.jiva.workflow.model.TaskState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Edelmann
 */
public class ReleaseTaskOperation extends AbstractTaskOperation {


    public ReleaseTaskOperation() {
        super(TaskAction.Release);
    }

    @Override
    protected List<ConditionDescriptor> buildConditions() {
        List<ConditionDescriptor> conditions = new ArrayList<ConditionDescriptor>();
        Map<String, String> taskStateConditionParams = new HashMap<String, String>() {{
            put(IsTaskStateCondition.PARAM, TaskState.CLAIMED.name());
        }};
        conditions.add(OSWorkflowUtils.createConditionDescriptor(IsTaskStateCondition.class.getSimpleName(), taskStateConditionParams));
        conditions.add(OSWorkflowUtils.createConditionDescriptor(OnlyAssigneeCondition.class.getSimpleName(), new HashMap<String, String>()));
        return conditions;
    }

    @Override
    protected FunctionDescriptor buildFunction() {
        return OSWorkflowUtils.createFunctionDescriptor(ReleaseTaskFunction.class);
    }
}
