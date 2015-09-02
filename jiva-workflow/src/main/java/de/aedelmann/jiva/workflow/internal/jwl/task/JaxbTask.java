package de.aedelmann.jiva.workflow.internal.jwl.task;


import com.opensymphony.workflow.loader.AbstractDescriptor;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.ConditionsDescriptor;
import com.opensymphony.workflow.loader.DescriptorFactory;
import com.opensymphony.workflow.loader.RestrictionDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;
import de.aedelmann.jiva.workflow.internal.engine.conditions.IsTaskStateCondition;
import de.aedelmann.jiva.workflow.internal.engine.conditions.OnlyAssigneeCondition;
import de.aedelmann.jiva.workflow.internal.jwl.AbstractJaxbNode;
import de.aedelmann.jiva.workflow.internal.jwl.JaxbTransition;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.Constants;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.MappingContext;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.OSWorkflowUtils;
import de.aedelmann.jiva.workflow.internal.jwl.task.actions.ClaimTaskOperation;
import de.aedelmann.jiva.workflow.internal.jwl.task.actions.ReleaseTaskOperation;
import de.aedelmann.jiva.workflow.internal.jwl.task.actions.TaskOperation;
import de.aedelmann.jiva.workflow.internal.engine.functions.PeopleResolutionFunction;
import de.aedelmann.jiva.workflow.internal.engine.functions.UnassignFunction;
import de.aedelmann.jiva.workflow.jwl.Deadline;
import de.aedelmann.jiva.workflow.jwl.Task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.aedelmann.jiva.workflow.jwl.Assignment;
import de.aedelmann.jiva.workflow.jwl.Transition;
import de.aedelmann.jiva.workflow.model.TaskAction;
import de.aedelmann.jiva.workflow.model.TaskState;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * @author Alexander Edelmann
 */
public class JaxbTask extends AbstractJaxbNode implements Task {

    @XmlElement(type = JaxbAssignment.class)
    private Assignment assignment = null;

    private static Map<String, TaskOperation> TASK_OPERATIONS = new HashMap();

    static {
        TASK_OPERATIONS.put(TaskAction.Claim.getId(), new ClaimTaskOperation());
        TASK_OPERATIONS.put(TaskAction.Release.getId(), new ReleaseTaskOperation());
    }

    @Override
    protected String getNodeType() {
        return "task";
    }

    @Override
    public Assignment getAssignment() {
        return assignment;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbstractDescriptor map(MappingContext context) {
        StepDescriptor stepDescriptor = (StepDescriptor)super.map(context);
        stepDescriptor.getPreFunctions().add(OSWorkflowUtils.createFunctionDescriptor(PeopleResolutionFunction.class.getSimpleName()));

        DescriptorFactory factory = new DescriptorFactory();
        for (Object actionDescriptorObject : stepDescriptor.getActions()) {
            ActionDescriptor ad = (ActionDescriptor)actionDescriptorObject;
            ConditionsDescriptor andDescriptor = factory.createConditionsDescriptor();
            andDescriptor.setType(Constants.CONDITION_TYPE_AND);

            Map<String, String> taskStateConditionParams = new HashMap<String, String>() {{
                put(IsTaskStateCondition.PARAM, TaskState.CLAIMED.name());
            }};
            andDescriptor.getConditions().add(OSWorkflowUtils.createConditionDescriptor(IsTaskStateCondition.class.getSimpleName(), taskStateConditionParams));
            andDescriptor.getConditions().add(OSWorkflowUtils.createConditionDescriptor(OnlyAssigneeCondition.class.getSimpleName(), new HashMap<String, String>(0)));
            RestrictionDescriptor restrictionDescriptor = new RestrictionDescriptor();
            restrictionDescriptor.setConditionsDescriptor(andDescriptor);
            ad.setRestriction(restrictionDescriptor);

        }

        for (TaskOperation taskOperation : TASK_OPERATIONS.values()) {
            stepDescriptor.getActions().add(taskOperation.build(context,stepDescriptor));
        }


        return stepDescriptor;
    }

    @Override
    public Deadline getCompletionDeadline() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Transition getTransitionByName(String id) {
        if (TASK_OPERATIONS.containsKey(id)) {
            return new JaxbTransition(id,this);
        } else {
            return super.getLeavingTransitionByName(id);
        }
    }
}
