package com.camunda.platform.variable;


import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.ReflectUtil;
import org.activiti.engine.impl.variable.ValueFields;
import org.activiti.engine.impl.variable.VariableType;

public class JAXBVariableType implements VariableType {

	static final String VARIABLE_NAME = "JAXBVariableType";

	@Override
	public String getTypeName() {
		return VARIABLE_NAME;
	}

	@Override
	public boolean isCachable() {
		return false;
	}

	@Override
	public boolean isAbleToStore(Object value) {
		XmlRootElement xmlRootElement = value.getClass().getAnnotation(XmlRootElement.class);
		return xmlRootElement != null;
	}

	@Override
	public void setValue(Object value, ValueFields valueFields) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {value.getClass() });
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(value, stringWriter);
			valueFields.setTextValue(stringWriter.toString());
		} catch (JAXBException e) {
			throw new ActivitiException("Problem marshalling variable", e);
		}
	}

	@Override
	public Object getValue(ValueFields valueFields) {
		// Really dirty...
		String packageName = valueFields.getTextValue().subSequence(
				valueFields.getTextValue().indexOf("xmlns=\"") + 7,
				valueFields.getTextValue().indexOf("\">")).toString();
		
		String className = valueFields.getTextValue().subSequence(
				valueFields.getTextValue().indexOf("\n<") + 2,
				valueFields.getTextValue().indexOf(" xmlns")).toString();
		
		// Make first letter upper case
		className = className.replaceFirst("(.)", (className.charAt(0) + "").toUpperCase());
		
		String fullyQualifiedName = packageName + "." + className;

		// End of really dirty code ;)
		
		try {
			Class clazz = ReflectUtil.loadClass(fullyQualifiedName);
		
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller um = jc.createUnmarshaller();
			Object o = um.unmarshal(new StringReader(valueFields.getTextValue()));
			return o;
		 } catch (JAXBException e) {
			 throw new ActivitiException("Problem unmarshalling variable", e);
		 }
	}

}
