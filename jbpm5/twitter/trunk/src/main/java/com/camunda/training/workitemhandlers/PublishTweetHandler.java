package com.camunda.training.workitemhandlers;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

public class PublishTweetHandler implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		// extract parameters
		String tweet = (String) workItem.getParameter("Tweet");
		String email = (String) workItem.getParameter("Email");

		// business logic
		System.out.println("Publishing tweet '" + tweet + "' from " + email);

		// notify manager that work item has been completed
		manager.completeWorkItem(workItem.getId(), null);
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// Do nothing...
		System.err.println("oops, someone wants us to abort the work item...");
	}
}
