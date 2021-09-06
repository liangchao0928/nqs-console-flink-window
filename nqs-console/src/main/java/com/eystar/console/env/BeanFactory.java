package com.eystar.console.env;


import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.Resources;
import org.apache.flink.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.net.URL;


public class BeanFactory extends DefaultListableBeanFactory {

	public static Logger LOG = LoggerFactory.getLogger(BeanFactory.class);

	public static final String SPRING_BEAN_FACTORY_XML = "springframework.bean.factory.xml";

	public static final String SPRING_BEAN_FACTORY_NAME = "springframework.bean.factory.name";

	private static BeanFactory instance;

	public static BeanFactory getInstance() {
		return instance;
	}

	public static void setInstance(BeanFactory instance) {
		BeanFactory.instance = instance;
	}

	private transient final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

	private String xml;


	public BeanFactory(String location) {
		super();
		try {
			Resource resource = reader.getResourceLoader().getResource(location);
			URL url = resource.getURL();
			this.xml = Resources.toString(url, Charsets.UTF_8);
			this.reader.setValidating(false);
			this.reader.loadBeanDefinitions(resource);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}


	public BeanFactory(Configuration stormConf) {
		this.xml = stormConf.getString(SPRING_BEAN_FACTORY_XML,null);
		System.out.println("----->>>>> xml content is :"+xml);
		LOG.info("----->>>>> xml content is :{}"+xml);
		this.reader.setValidating(false);
		this.reader.loadBeanDefinitions(new ByteArrayResource(xml.getBytes(Charsets.UTF_8)));

	}


	public static ApplicationContext getBeanFactory(Configuration globalJobParameters) {
		String xmlName = globalJobParameters.getString(BeanFactory.SPRING_BEAN_FACTORY_NAME, null);
//		System.out.println("xmlName is :"+xmlName);
		ApplicationContext beanConf = new ClassPathXmlApplicationContext(xmlName);
		return beanConf;
	}

	public String getXml() {
		return xml;
	}



}
