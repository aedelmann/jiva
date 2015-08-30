package de.aedelmann.jiva.workflow.model;


public interface SubtaskInstance extends TaskInstance {

    TaskInstance getParentTask();
}
