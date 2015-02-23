package com.camunda.platform.configuration;


import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.variable.SerializableType;

import com.camunda.platform.variable.JAXBVariableType;

public class JAXBVariableStandaloneInMemProcessEngineConfiguration
		extends StandaloneInMemProcessEngineConfiguration {

	public JAXBVariableStandaloneInMemProcessEngineConfiguration() {
		super();
		initVariableTypes();
		initCustomVariableTypes();
	}

	protected void initCustomVariableTypes() {
		// We try adding the variable type right before SerializableType, if available
		int serializableIndex = variableTypes.getTypeIndex(SerializableType.TYPE_NAME);
		if (serializableIndex > -1) {
			variableTypes.addType(new JAXBVariableType(), serializableIndex);
		} else {
			variableTypes.addType(new JAXBVariableType());
		}
	}

}
