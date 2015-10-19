package de.aedelmann.jiva.workflow;

import java.io.IOException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.aedelmann.jiva.workflow.engine.WorkflowEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationcontext.xml" })
@Transactional
public abstract class AbstractWorkflowTest {

	@Autowired
	protected WorkflowService workflowService;

	@Autowired
	protected WorkflowModelStore modelStore;

	@Before
	public void setupTest() {
		WorkflowEnvironment.setCurrentUserId("alex");
	}

	protected void loginAs(String userId) {
		WorkflowEnvironment.setCurrentUserId("alex");
	}

	protected void registerModel(String workflowFile) {
		try {
			modelStore.addModel(new ClassPathResource(workflowFile).getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
