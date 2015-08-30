package de.aedelmann.jiva.workflow.internal.jwl.mapping;

import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.ConditionDescriptor;
import com.opensymphony.workflow.loader.DescriptorFactory;
import com.opensymphony.workflow.loader.FunctionDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;
import com.opensymphony.workflow.loader.ValidatorDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Edelmann
 */
public class OSWorkflowUtils {

    @SuppressWarnings("unchecked")
    public static StepDescriptor findStepByName(final WorkflowDescriptor descriptor, final String name) {

        List<StepDescriptor> steps = descriptor.getSteps();

        for (StepDescriptor stepDescriptor : steps) {

            if (stepDescriptor.getName().equals(name)) {
                return stepDescriptor;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static ActionDescriptor findActionByName(final StepDescriptor descriptor, final String name) {
        List<ActionDescriptor> actions = descriptor.getActions();

        for (ActionDescriptor actionDescriptor : actions) {

            if (actionDescriptor.getName().equals(name)) {
                return actionDescriptor;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static FunctionDescriptor createFunctionDescriptor(final String clazz) {
        DescriptorFactory factory = new DescriptorFactory();
        FunctionDescriptor fd = factory.createFunctionDescriptor();
        fd.setName(clazz);
        fd.setType(Constants.TYPE_SPRING);
        fd.getArgs().put(Constants.BEAN_ARG, clazz);

        return fd;
    }

    @SuppressWarnings("unchecked")
    public static FunctionDescriptor createFunctionDescriptor(final Class<?> clazz) {
        return createFunctionDescriptor(clazz.getSimpleName());
    }

    public static FunctionDescriptor createFunctionDescriptor(final Class<?> clazz, Map<String, String> params) {
        FunctionDescriptor fd = createFunctionDescriptor(clazz.getName());
        for (String paramKey : params.keySet()) {
            fd.getArgs().put(paramKey, params.get(paramKey));
        }
        return fd;
    }

    public static ValidatorDescriptor createValidatorDescriptor(String clazz, Map<String, String> params) {
        DescriptorFactory factory = new DescriptorFactory();
        ValidatorDescriptor vd = factory.createValidatorDescriptor();
        vd.setType(Constants.TYPE_CLASS);
        vd.getArgs().put(Constants.CLASSNAME_ARG, clazz);
        for (String paramKey : params.keySet()) {
            vd.getArgs().put(paramKey, params.get(paramKey));
        }
        return vd;
    }

    public static ConditionDescriptor createConditionDescriptor(String clazz, Map<String, String> params) {
        DescriptorFactory factory = new DescriptorFactory();
        ConditionDescriptor vd = factory.createConditionDescriptor();
        vd.setType(Constants.TYPE_SPRING);
        vd.getArgs().put(Constants.BEAN_ARG, clazz);
        for (String paramKey : params.keySet()) {
            vd.getArgs().put(paramKey, params.get(paramKey));
        }
        return vd;
    }

}

/* EOF */
