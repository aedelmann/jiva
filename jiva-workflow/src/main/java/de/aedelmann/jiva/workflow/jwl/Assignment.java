package de.aedelmann.jiva.workflow.jwl;

import java.util.List;

import de.aedelmann.jiva.workflow.extensionpoints.PeopleResolutionProvider;

/**
 * @author Alexander Edelmann
 */
public interface Assignment {

    List<PeopleResolutionProvider> getProviders();
}
