package de.aedelmann.jiva.workflow.deployment;

import java.io.InputStream;

/**
 * Deployment Bundle contains everything needed for the workflow application
 * e.g. workflow model, screens, permissions, ...
 *
 * @author Alexander Edelmann
 */
public interface MutableDeployment extends Deployment {

    void setAuthor(String author);

    void read(InputStream modelInputStream) throws CannotReadModelException;


}
