package de.aedelmann.jiva.workflow.runtime;


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

    WorkflowInstance getWorkflowInstance();
}
