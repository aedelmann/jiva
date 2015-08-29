package de.aedelmann.jiva.workflow.internal.deployment;

import de.aedelmann.jiva.workflow.deployment.CannotReadModelException;
import de.aedelmann.jiva.workflow.deployment.MutableDeployment;
import de.aedelmann.jiva.workflow.internal.assignment.JaxbLiteralProvider;
import de.aedelmann.jiva.workflow.internal.jwl.JaxbEnd;
import de.aedelmann.jiva.workflow.internal.jwl.JaxbStart;
import de.aedelmann.jiva.workflow.internal.jwl.JaxbTransition;
import de.aedelmann.jiva.workflow.internal.jwl.JaxbWorkflow;
import de.aedelmann.jiva.workflow.internal.jwl.task.JaxbAssignment;
import de.aedelmann.jiva.workflow.internal.jwl.task.JaxbTask;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Date;

/**
 * @author Alexander Edelmann
 */
public class DefaultDeployment implements MutableDeployment {

    private WorkflowModel workflowModel;

    private Date createdOn;

    private String author;

    private static final Class[] JAXB_CLASSES = new Class[] {JaxbTask.class,
                                                             JaxbAssignment.class,
                                                             JaxbLiteralProvider.class,
                                                             JaxbWorkflow.class,
                                                             JaxbTransition.class,
                                                             JaxbStart.class,
                                                             JaxbEnd.class};

    public DefaultDeployment() {
        this.createdOn = new Date();
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void read(InputStream workflowModelStream) throws CannotReadModelException {
        try {
            String workflowAsXml = getStringFromInputStream(workflowModelStream);
            JAXBContext jc = JAXBContext.newInstance(JAXB_CLASSES);
            Unmarshaller u = jc.createUnmarshaller();
            JaxbWorkflow workflow =  (JaxbWorkflow) u.unmarshal(new StringReader(workflowAsXml));
            workflow.setXml(workflowAsXml);
            workflow.init();
            this.workflowModel = workflow;
        } catch(JAXBException jaxbException) {
            throw new RuntimeException("Problem parsing the workflow",jaxbException);
        }
    }

    private String getStringFromInputStream(InputStream is) {
        try {

            return IOUtils.toString(is, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException("Problem converting inputstream to string",e);
        }
    }

    public boolean isRead() {
        return workflowModel != null;
    }

    @Override
    public String getId() {
        return isRead() ? workflowModel.getId() : null;
    }

    @Override
    public Date getCreatedOn() {
        return createdOn;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public WorkflowModel getWorkflowModel() {
        return workflowModel;
    }

}
