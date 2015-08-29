package de.aedelmann.jiva.workflow.internal.jwl.task;


import com.opensymphony.workflow.loader.AbstractDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.AbstractJaxbNode;
import de.aedelmann.jiva.workflow.internal.jwl.mapper.MappingContext;
import de.aedelmann.jiva.workflow.internal.jwl.mapper.OSWorkflowUtils;
import de.aedelmann.jiva.workflow.internal.jwl.task.actions.ClaimTaskOperation;
import de.aedelmann.jiva.workflow.internal.jwl.task.actions.ReleaseTaskOperation;
import de.aedelmann.jiva.workflow.internal.jwl.task.actions.TaskOperation;
import de.aedelmann.jiva.workflow.internal.runtime.functions.PeopleResolutionFunction;
import de.aedelmann.jiva.workflow.internal.runtime.functions.UnassignFunction;
import de.aedelmann.jiva.workflow.jwl.Deadline;
import de.aedelmann.jiva.workflow.jwl.Task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import de.aedelmann.jiva.workflow.jwl.Assignment;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * @author Alexander Edelmann
 */
public class JaxbTask extends AbstractJaxbNode implements Task {

    @XmlElement(type = JaxbAssignment.class)
    private Assignment assignment = null;

    private static List<TaskOperation> TASK_OPERATIONS = new ArrayList<TaskOperation>();

    static {
        TASK_OPERATIONS.add(new ClaimTaskOperation());
        TASK_OPERATIONS.add(new ReleaseTaskOperation());
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
        stepDescriptor.getPreFunctions().add(OSWorkflowUtils.createFunctionDescriptor(UnassignFunction.class));
        stepDescriptor.getPreFunctions().add(OSWorkflowUtils.createFunctionDescriptor(PeopleResolutionFunction.class));

        for (TaskOperation taskOperation : TASK_OPERATIONS) {
            stepDescriptor.getActions().add(taskOperation.build(context,stepDescriptor));
        }
        return stepDescriptor;
    }

    @Override
    public Deadline getCompletionDeadline() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
