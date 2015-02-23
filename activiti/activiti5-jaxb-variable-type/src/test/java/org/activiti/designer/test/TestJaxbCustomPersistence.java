package org.activiti.designer.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiTestCase;
import org.activiti.engine.test.Deployment;

import com.camunda.platform.configuration.JAXBVariableStandaloneInMemProcessEngineConfiguration;
import com.camunda.platform.dataobject.MyJaxbDataObject;

public class TestJaxbCustomPersistence extends ActivitiTestCase {

	@Override
	protected void initializeProcessEngine() {
		ProcessEngineConfiguration configuration = new JAXBVariableStandaloneInMemProcessEngineConfiguration();
		processEngine = configuration.buildProcessEngine();
	}
	
	@Deployment(resources="diagrams/MyProcess.bpmn20.xml")
	public void testProcess() {
		Map<String, Object> variables = new HashMap<String, Object>();

		variables.put("someObject", new MyJaxbDataObject());
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("MyProcess", variables);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		
		MyJaxbDataObject myJaxbDataObject = (MyJaxbDataObject)runtimeService.getVariable(processInstance.getId(), "someObject");
		
		System.out.println("Here is the unmarshalled object: " + myJaxbDataObject.getSomeString());
		
		Task task = taskService.createTaskQuery().taskAssignee("kermit").singleResult();
		
		variables.clear();
		variables.put("someObject", new MyJaxbDataObject());
		taskService.complete(task.getId(), variables);
		
		assertProcessEnded(processInstance.getId());
	}
}
