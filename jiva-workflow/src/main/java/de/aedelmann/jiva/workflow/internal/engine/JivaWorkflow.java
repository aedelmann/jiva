package de.aedelmann.jiva.workflow.internal.engine;


import com.opensymphony.workflow.AbstractWorkflow;
import com.opensymphony.workflow.config.SpringConfiguration;
import com.opensymphony.workflow.util.SpringTypeResolver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Alexander Edelmann
 */
public class JivaWorkflow extends AbstractWorkflow {

    @Autowired
    public JivaWorkflow(SpringConfiguration configuration,  SpringTypeResolver resolver) {
        setConfiguration(configuration);
        setResolver(resolver);
        this.context = new JivaWorkflowContext();
    }
}
