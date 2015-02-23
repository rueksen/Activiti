package com.camunda.training;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkItem;
import org.jbpm.JbpmJUnitTestCase;
import org.junit.Test;

import com.camunda.training.workitemhandlers.NotificationHandler;
import com.camunda.training.workitemhandlers.PublishTweetHandler;

public class ProcessTest extends JbpmJUnitTestCase {

	@Test
	public void testApprovedPath() throws Exception {
		StatefulKnowledgeSession ksession = createKnowledgeSession("TwitterDemoProcess.bpmn");
		KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
				.newFileLogger(ksession, "test");

		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
				testHandler);

		// ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
		// new MyHumanTaskHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("Notification",
				new NotificationHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("publish-tweet",
				new PublishTweetHandler());

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("Tweet", "Testing jBPM 5");
		variables.put("Email", "n.preusker@googlemail.com");
		// variables.put("approved", false);

		// start a new process instance
		ProcessInstance processInstance = ksession.startProcess(
				"TwitterDemoProcess", variables);

		assertProcessInstanceActive(processInstance.getId(), ksession);

		// assertProcessInstanceCompleted(processInstance.getId(), ksession);
		// check whether the given nodes were executed during the process
		// execution
		assertNodeTriggered(processInstance.getId(), "StartProcess",
				"Review Tweet");

		WorkItem task = testHandler.getWorkItem();

		variables.clear();
		variables.put("approved", true);

		ksession.getWorkItemManager().completeWorkItem(task.getId(), variables);

		assertProcessInstanceCompleted(processInstance.getId(), ksession);
		assertNodeTriggered(processInstance.getId(), "StartProcess",
				"Review Tweet", "Publish Tweet");

		logger.close();
	}

	@Test
	public void testRejectedPath() throws Exception {
		StatefulKnowledgeSession ksession = createKnowledgeSession("TwitterDemoProcess.bpmn");
		KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
				.newFileLogger(ksession, "test");

		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
				testHandler);

		// ksession.getWorkItemManager().registerWorkItemHandler("Human Task",
		// new MyHumanTaskHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("Notification",
				new NotificationHandler());
		ksession.getWorkItemManager().registerWorkItemHandler("publish-tweet",
				new PublishTweetHandler());

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("Tweet", "Testing jBPM 5");
		variables.put("Email", "n.preusker@googlemail.com");
		// variables.put("approved", false);

		// start a new process instance
		ProcessInstance processInstance = ksession.startProcess(
				"TwitterDemoProcess", variables);

		assertProcessInstanceActive(processInstance.getId(), ksession);

		// assertProcessInstanceCompleted(processInstance.getId(), ksession);
		// check whether the given nodes were executed during the process
		// execution
		assertNodeTriggered(processInstance.getId(), "StartProcess",
				"Review Tweet");

		WorkItem task = testHandler.getWorkItem();

		variables.clear();
		variables.put("approved", false);

		ksession.getWorkItemManager().completeWorkItem(task.getId(), variables);

		assertProcessInstanceCompleted(processInstance.getId(), ksession);
		assertNodeTriggered(processInstance.getId(), "StartProcess",
				"Review Tweet", "Send Rejection");

		logger.close();
	}
	
	@Test
	public void testStandalone() {
    // deploy process
    KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    kbuilder.add(ResourceFactory.newClassPathResource("TwitterDemoProcess.bpmn"), ResourceType.BPMN2);
    KnowledgeBase kbase = kbuilder.newKnowledgeBase();
    StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
    
    // instantiate and register Java classes of process
    ksession.getWorkItemManager().registerWorkItemHandler("Human Task",    new MyHumanTaskHandler());
    ksession.getWorkItemManager().registerWorkItemHandler("Notification",  new NotificationHandler());
    ksession.getWorkItemManager().registerWorkItemHandler("publish-tweet", new PublishTweetHandler());
    
    // create process variables
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("Tweet", "Testing jBPM 5");
    variables.put("Email", "info@camunda.com");
    
    // start process instance
    ProcessInstance processInstance = ksession.startProcess("TwitterDemoProcess", variables);

    // test
    assertProcessInstanceActive(processInstance.getId(), ksession);
	}
	
}
