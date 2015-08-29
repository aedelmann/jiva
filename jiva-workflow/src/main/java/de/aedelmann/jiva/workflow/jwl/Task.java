package de.aedelmann.jiva.workflow.jwl;


/**
 * @author Alexander Edelmann
 */
public interface Task extends Node, DeadlineAware {

    Assignment getAssignment();
}
