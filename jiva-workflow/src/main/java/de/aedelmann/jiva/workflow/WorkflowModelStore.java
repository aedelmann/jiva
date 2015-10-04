package de.aedelmann.jiva.workflow;

import java.io.InputStream;
import java.util.List;

import de.aedelmann.jiva.workflow.jwl.WorkflowModel;

/**
 * @author Alexander Edelmann
 */
public interface WorkflowModelStore {

	WorkflowModel getModel(String name);
	
	
	
	List<WorkflowModel> list();
	
	void addModel(InputStream inputStream);

}
