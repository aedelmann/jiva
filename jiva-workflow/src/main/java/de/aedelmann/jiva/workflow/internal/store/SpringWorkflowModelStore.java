package de.aedelmann.jiva.workflow.internal.store;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.xbean.spring.context.ResourceXmlApplicationContext;
import org.apache.xbean.spring.context.SpringApplicationContext;
import org.apache.xbean.spring.context.SpringXmlPreprocessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import de.aedelmann.jiva.workflow.WorkflowModelStore;
import de.aedelmann.jiva.workflow.internal.jwl.WorkflowModelImpl;
import de.aedelmann.jiva.workflow.internal.store.preprocessors.ComponentScanPreProcessor;
import de.aedelmann.jiva.workflow.internal.store.preprocessors.WorkflowModelXbeanPreProcessor;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;

@Component
public class SpringWorkflowModelStore implements WorkflowModelStore {
	
	private Map<String,WorkflowModel> models = new HashMap<>();

	private static final WorkflowModelXbeanPreProcessor WF_INJECTTION_PROCESSOR = new WorkflowModelXbeanPreProcessor();
	private static final ComponentScanPreProcessor REGISTER_RESOURCEBUNDLE = new ComponentScanPreProcessor();

	private ApplicationContext parentContext;
	
	public static ThreadLocal<WorkflowModelImpl> tlData = new ThreadLocal<WorkflowModelImpl>();
	
	@Override
	public WorkflowModel getModel(String name) {
		return models.get(name);
	}
	
	public void addModel(InputStream is) {
		byte[] workflowAsString;
		try {
			workflowAsString = IOUtils.toByteArray(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		WorkflowModelImpl model = new WorkflowModelImpl();
		model.setXml(new String(workflowAsString));
		tlData.set(model);
		
		SpringApplicationContext ctx;
		try {
			ctx = (SpringApplicationContext) getContext(
					new InputStreamResource(new ByteArrayInputStream(workflowAsString)).getInputStream(), model);
		} catch (Exception e) {
			throw new RuntimeException("could not load workflow",e);
		}

		model.setApplicationContext(ctx);
		
		this.models.put(model.getName(), model);
	}

	public ApplicationContext getContext(InputStream is, WorkflowModelImpl model) {
		InputStreamResource isr = new InputStreamResource(is);
		try {
			ResourceXmlApplicationContext ctx = new ResourceXmlApplicationContext(isr, getPreprocessors(),
					parentContext, Collections.emptyList(), false);// {
			ctx.setValidating(false);
			ctx.refresh();
			return ctx;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected List<SpringXmlPreprocessor> getPreprocessors() {
		List<SpringXmlPreprocessor> preProcessors = new ArrayList<SpringXmlPreprocessor>(3);
		preProcessors.add(WF_INJECTTION_PROCESSOR);
		preProcessors.add(REGISTER_RESOURCEBUNDLE);
		return preProcessors;
	}

	@Override
	public List<WorkflowModel> list() {
		return new ArrayList<WorkflowModel>(models.values());
	}

}
