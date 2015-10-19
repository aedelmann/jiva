package de.aedelmann.jiva.workflow;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class WorkflowModelStoreTest extends AbstractWorkflowTest {

	@Test
	public void testGetWorkflowModel() throws Exception {
		modelStore.addModel(new ClassPathResource("sample.jwl").getInputStream());
		assertEquals("approval",modelStore.getModel("approval").getName());
	}
	
}
