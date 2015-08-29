package de.aedelmann.jiva.workflow.internal.jwl.task.actions;

import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.ConditionDescriptor;
import com.opensymphony.workflow.loader.ConditionsDescriptor;
import com.opensymphony.workflow.loader.DescriptorFactory;
import com.opensymphony.workflow.loader.FunctionDescriptor;
import com.opensymphony.workflow.loader.RestrictionDescriptor;
import com.opensymphony.workflow.loader.ResultDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.Constants;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.MappingContext;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.OSWorkflowUtils;

import java.util.List;

/**
 * @author Alexander Edelmann
 */
public abstract class AbstractTaskOperation implements TaskOperation {

    private String operationId;

    public AbstractTaskOperation(String operationId) {
        this.operationId = operationId;
    }

    @Override
    public ActionDescriptor build(MappingContext context, StepDescriptor taskDescriptor) {
        DescriptorFactory factory = new DescriptorFactory();
        ActionDescriptor ad = factory.createActionDescriptor();
        ad.setId(context.getActionGenerator().next());
        ad.setName(operationId);
        ad.setAutoExecute(false);

        ResultDescriptor ur = factory.createResultDescriptor();
        ur.setOldStatus("Finished");
        ur.setStatus(taskDescriptor.getName());

        ad.setParent(OSWorkflowUtils.findStepByName(context.getWorkflowDescriptor(),
                taskDescriptor.getName()));
        ad.getPostFunctions().add(buildFunction());

        ConditionsDescriptor andDescriptor = factory.createConditionsDescriptor();
        andDescriptor.setType(Constants.CONDITION_TYPE_AND);
        andDescriptor.getConditions().addAll(buildConditions());

        ad.setUnconditionalResult(ur);

        RestrictionDescriptor restrictionDescriptor = new RestrictionDescriptor();
        restrictionDescriptor.setConditionsDescriptor(andDescriptor);
        ad.setRestriction(restrictionDescriptor);

        ur.setStep(taskDescriptor.getId());

        return ad;
    }

    protected abstract List<ConditionDescriptor> buildConditions();

    protected abstract FunctionDescriptor buildFunction();
}
