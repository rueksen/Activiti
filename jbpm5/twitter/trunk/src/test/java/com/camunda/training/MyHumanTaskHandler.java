package com.camunda.training;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

public class MyHumanTaskHandler implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("excute...");
		
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("abort...");
		
	}

}
