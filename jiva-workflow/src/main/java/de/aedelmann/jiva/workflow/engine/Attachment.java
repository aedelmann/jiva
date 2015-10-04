package de.aedelmann.jiva.workflow.engine;


import java.util.Date;

/**
 * @author Alexander Edelmann
 */
public interface Attachment {

    String getId();

    String getContentType();

    Date getCreatedOn();

    String getAuthor();

    byte[] getContent();
}
