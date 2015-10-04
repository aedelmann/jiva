package de.aedelmann.jiva.workflow.internal.store.preprocessors;

import org.apache.xbean.spring.context.SpringApplicationContext;
import org.apache.xbean.spring.context.SpringXmlPreprocessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ComponentScanPreProcessor implements SpringXmlPreprocessor {

	private static final String SPRING_CTX_NS = "http://www.springframework.org/schema/context";

	public void preprocess(SpringApplicationContext applicationContext, XmlBeanDefinitionReader reader,
			Document document) {

		Element root = (Element) document.getFirstChild();
		Element componentScanEnabling = document.createElementNS(SPRING_CTX_NS, "component-scan");
		componentScanEnabling.setAttribute("base-package", "de.aedelmann.jiva.workflow");

		Element excludeServices = document.createElementNS(SPRING_CTX_NS, "exclude-filter");
		excludeServices.setAttribute("type", "regex");
		excludeServices.setAttribute("expression", "de.aedelmann.jiva.workflow.internal..*Service*");
		componentScanEnabling.appendChild(excludeServices);

		root.appendChild(componentScanEnabling);

	}
}

/* EOF */
