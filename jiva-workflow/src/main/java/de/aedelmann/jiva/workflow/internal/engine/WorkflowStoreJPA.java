package de.aedelmann.jiva.workflow.internal.engine;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.StoreException;
import com.opensymphony.workflow.spi.hibernate3.AbstractHibernateWorkflowStore;
import com.opensymphony.workflow.spi.hibernate3.SpringHibernateWorkflowStore;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

/**
 * @author Alexander Edelmann
 */
public class WorkflowStoreJPA extends SpringHibernateWorkflowStore {

    @PersistenceContext(unitName="jiva")
    private EntityManager em;

    @Override protected Object execute(AbstractHibernateWorkflowStore.InternalCallback action) throws StoreException {

        try {
            return action.doInHibernate((Session) em.getDelegate());
        } catch (StoreException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void init(Map props) throws StoreException {
        ;
    }

    @Override public PropertySet getPropertySet(long workflowId) {
        return null;
    }

}
