package de.aedelmann.jiva.workflow.engine;


import java.util.Date;
import java.util.List;

/**
 * @author Alexander Edelmann
 */
public interface Comment {

    String getId();

    Date getCreatedOn();

    String getMessage();

    String getAuthor();

    List<Comment> getReplies();

    WorkflowExecution getWorkflowInstance();
}
