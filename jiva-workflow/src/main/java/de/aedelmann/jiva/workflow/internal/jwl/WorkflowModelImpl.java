package de.aedelmann.jiva.workflow.internal.jwl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ConfigurableApplicationContext;

import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;
import de.aedelmann.jiva.workflow.jwl.Start;
import de.aedelmann.jiva.workflow.jwl.Step;
import de.aedelmann.jiva.workflow.jwl.Transition;
import de.aedelmann.jiva.workflow.jwl.Validatable;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;

public class WorkflowModelImpl extends WorkflowElement implements WorkflowModel {

    protected String workflowModelXml = null;

    public Map<String, Object> getMetaAttributes() {
        Map<String, MetaAttributes> attributes = applicationContext.getBeansOfType(
                MetaAttributes.class);

        if (attributes.size() == 1) {
            return attributes.values().iterator().next().getEntries();
        }

        return Collections.emptyMap();
    }

    public Start getStart() {
        return get(Start.class);
    }

    public Step getStep(String name) {
        return (Step) applicationContext.getBean(name);
    }

    public String[] getNodeNames() {
        return applicationContext.getBeanNamesForType(Step.class);
    }

    public String getXml() {
        return workflowModelXml;
    }

    public void setXml(String xml) {
        this.workflowModelXml = xml;
    }


    public Map<String, Step> getNext(final String id) {
        Map<String, Step> nextNodes = new HashMap<String, Step>();

        final Step currentNode = getStep(id);

        for (Transition t : currentNode.getTransitions()) {
            nextNodes.put(t.getTo().getName(), t.getTo());
        }

        return nextNodes;
    }

    public Map<String, Step> getPrevious(final String nodeId) {
        Map<String, Step> previousNodes = new HashMap<String, Step>();

        for (int i = 0; i < getNodeNames().length; i++) {
        	Step currentNode = (Step) applicationContext.getBean(getNodeNames()[i]);

            for (Transition t : currentNode.getTransitions()) {

                if (t.getTo().getName().equals(nodeId)) {
                    previousNodes.put(currentNode.getName(), currentNode);
                }
            }
        }

        return previousNodes;
    }

    public void validate() throws ModelValidationProblem {

        for (int i = 0; i < getNodeNames().length; i++) {
            Validatable currentNode = (Validatable) applicationContext.getBean(getNodeNames()[i]);
            currentNode.validate();
        }
    }
    
    public String toString() {
        return "Workflow-Model '" + getName() + "'";
    }

    @SuppressWarnings("unchecked")
    private <N> N get(Class<N> type) {
        String[] names = applicationContext.getBeanNamesForType(type);

        if (names.length == 1) {
            return (N) applicationContext.getBean(names[0]);
        }

        return null;

    }

    @Override public void setName(String workflowName) {
        this.name = workflowName;

        if (applicationContext != null) {
            updateModel(workflowName);
        }
    }

    private void updateModel(String workflowName) {
        String[] nodeIds = getNodeNames();

        for (String nodeId : nodeIds) {
            WorkflowElement element = (WorkflowElement) getStep(nodeId);
            element.setWorkflowName(workflowName);
        }
    }

	public void destroy() {
		 ConfigurableApplicationContext ctx = (ConfigurableApplicationContext)getApplicationContext();
		 ctx.close();
	}

}

/* EOF */
