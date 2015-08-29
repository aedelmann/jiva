package de.aedelmann.jiva.workflow.internal.jwl.task.actions;

import com.opensymphony.workflow.loader.ConditionDescriptor;
import com.opensymphony.workflow.loader.FunctionDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.OSWorkflowUtils;
import de.aedelmann.jiva.workflow.internal.runtime.conditions.IsAssignedCondition;
import de.aedelmann.jiva.workflow.internal.runtime.functions.taskoperations.ClaimTaskFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alexander Edelmann
 */
public class ClaimTaskOperation extends AbstractTaskOperation {


    public ClaimTaskOperation() {
        super("jiva.taskoperation.claim");
    }

    @Override
    protected List<ConditionDescriptor> buildConditions() {
        List<ConditionDescriptor> conditions = new ArrayList<ConditionDescriptor>();
//        conditions.add(OSWorkflowUtils.createConditionDescriptor(IsTaskStateCondition.class.getName(), MapBuilder.<String, String>newBuilder().add(IsTaskStateCondition.PARAM_TASK_STATE, WorkflowState.READY.name()).toHashMap()));
        conditions.add(OSWorkflowUtils.createConditionDescriptor(IsAssignedCondition.class.getName(),new HashMap<String, String>()));
        return conditions;
    }

    @Override
    protected FunctionDescriptor buildFunction() {
        return OSWorkflowUtils.createFunctionDescriptor(ClaimTaskFunction.class);
    }
}
