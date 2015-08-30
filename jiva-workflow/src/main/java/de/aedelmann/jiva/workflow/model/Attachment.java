package de.aedelmann.jiva.workflow.model;


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
