package de.aedelmann.jiva.workflow.runtime;

/**
 * @author Alexander Edelmann
 */
public enum WorkflowState {
    CREATED("jiva.workflow.state.created"),
    READY("jiva.workflow.state.ready"),
    CLAIMED("jiva.workflow.state.claimed"),
    IN_PROGRESS("jiva.workflow.state.inprogress"),
    COMPLETED("jiva.workflow.state.completed"),
    OBSOLETE("jiva.workflow.state.obsolete");

    String i18nKey;

    WorkflowState(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public String getI18nKey(){
        return i18nKey;
    }
}
