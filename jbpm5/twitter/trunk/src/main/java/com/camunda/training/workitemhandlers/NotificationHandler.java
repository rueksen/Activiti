package com.camunda.training.workitemhandlers;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;


public class NotificationHandler implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		// extract parameters

		String email = (String) workItem.getParameter("Email");

		// send email
		System.out.println("Sending rejection notification to " + email);

		// notify manager that work item has been completed
		manager.completeWorkItem(workItem.getId(), null);
	}

	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		// Do nothing, notifications cannot be aborted
		System.err.println("oops, someone wants us to abort the work item...");
	}

}
