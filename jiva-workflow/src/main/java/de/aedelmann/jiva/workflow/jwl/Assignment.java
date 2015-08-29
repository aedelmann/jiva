package de.aedelmann.jiva.workflow.jwl;

import de.aedelmann.jiva.workflow.assignment.PeopleResolutionProvider;

import java.util.List;

/**
 * @author Alexander Edelmann
 */
public interface Assignment {

    List<PeopleResolutionProvider> getProviders();
}
