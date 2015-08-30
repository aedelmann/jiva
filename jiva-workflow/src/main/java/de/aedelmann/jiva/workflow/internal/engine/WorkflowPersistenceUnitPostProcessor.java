package de.aedelmann.jiva.workflow.internal.engine;

import de.aedelmann.jiva.workflow.internal.model.OSWorkflowInstance;
import de.aedelmann.jiva.workflow.internal.model.WorkItem;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alexander Edelmann
 */
public class WorkflowPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

    private static final Set<String> ENTITY_CLASSES = new HashSet<String>();

    static {
        // register jpa workflow entities here
        ENTITY_CLASSES.add(OSWorkflowInstance.class.getName());
        ENTITY_CLASSES.add(WorkItem.class.getName());
    }


    @Override
    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
        pui.getManagedClassNames().addAll(ENTITY_CLASSES);
        pui.addMappingFileName("META-INF/HibernateCurrentStep.hbm.xml");
        pui.addMappingFileName("META-INF/HibernateHistoryStep.hbm.xml");
        pui.addMappingFileName("META-INF/HibernateWorkflowEntry.hbm.xml");
    }
}