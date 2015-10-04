package de.aedelmann.jiva.workflow.internal.jwl;

import java.util.Map;

import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;


/**
 * @org.apache.xbean.XBean  element="meta" namespace="http://www.github.com/aedelmann/jiva/workflow/jwl"
 */
public class MetaAttributes extends WorkflowElement {

    private Map<String, Object> entries;


    /**
     * @org.apache.xbean.Map  keyName="key" entryName="entry"
     */
    public Map<String, Object> getEntries() {
        return this.entries;
    }

    public void setEntries(Map<String, Object> entries) {
        this.entries = entries;
    }

    @Override public void validate() {

        if ((entries == null) || (entries.size() == 0)) {
            throw new ModelValidationProblem(
                "Meta Attributes element does not contain any attributes!");
        }
    }

}

/* EOF */
