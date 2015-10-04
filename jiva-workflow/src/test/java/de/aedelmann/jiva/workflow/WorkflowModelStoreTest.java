package de.aedelmann.jiva.workflow;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationcontext.xml"})
public class WorkflowModelStoreTest {

	@Autowired
	private WorkflowModelStore modelStore;
	
	
	@Test
	public void testGetWorkflowModel() throws Exception {
		modelStore.addModel(new ClassPathResource("sample.jwl").getInputStream());
		assertEquals("approval",modelStore.getModel("approval").getName());
	}
	
}
