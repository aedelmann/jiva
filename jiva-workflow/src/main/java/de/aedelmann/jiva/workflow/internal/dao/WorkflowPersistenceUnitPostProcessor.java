package de.aedelmann.jiva.workflow.internal.dao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

import de.aedelmann.jiva.workflow.internal.engine.StepExecutionImpl;
import de.aedelmann.jiva.workflow.internal.engine.WorkItem;
import de.aedelmann.jiva.workflow.internal.engine.WorkflowExecutionImpl;
import de.aedelmann.jiva.workflow.internal.engine.history.HistoryStepExecutionImpl;

/**
 * @author Alexander Edelmann
 */
public class WorkflowPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

    private static final Set<String> ENTITY_CLASSES = new HashSet<String>();

    static {
        // register jpa workflow entities here
        ENTITY_CLASSES.add(WorkflowExecutionImpl.class.getName());
        ENTITY_CLASSES.add(WorkItem.class.getName());
        ENTITY_CLASSES.add(StepExecutionImpl.class.getName());
        ENTITY_CLASSES.add(HistoryStepExecutionImpl.class.getName());
    }


    @Override
    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
        pui.getManagedClassNames().addAll(ENTITY_CLASSES);
    }
}
