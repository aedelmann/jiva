package de.aedelmann.jiva.workflow.internal.jwl.task.actions;


import com.opensymphony.workflow.loader.ConditionDescriptor;
import com.opensymphony.workflow.loader.FunctionDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.OSWorkflowUtils;
import de.aedelmann.jiva.workflow.internal.runtime.conditions.OnlyAssigneeCondition;
import de.aedelmann.jiva.workflow.internal.runtime.functions.taskoperations.ReleaseTaskFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alexander Edelmann
 */
public class ReleaseTaskOperation extends AbstractTaskOperation {


    public ReleaseTaskOperation() {
        super("jiva.taskoperation.release");
    }

    @Override
    protected List<ConditionDescriptor> buildConditions() {
        List<ConditionDescriptor> conditions = new ArrayList<ConditionDescriptor>();
//        conditions.add(OSWorkflowUtils.createConditionDescriptor(IsTaskStateCondition.class.getName(), MapBuilder.<String, String>newBuilder().add(IsTaskStateCondition.PARAM_TASK_STATE, TaskState.CLAIMED.name()).toHashMap()));
        conditions.add(OSWorkflowUtils.createConditionDescriptor(OnlyAssigneeCondition.class.getName(), new HashMap<String, String>()));
        return conditions;
    }

    @Override
    protected FunctionDescriptor buildFunction() {
        return OSWorkflowUtils.createFunctionDescriptor(ReleaseTaskFunction.class);
    }
}
