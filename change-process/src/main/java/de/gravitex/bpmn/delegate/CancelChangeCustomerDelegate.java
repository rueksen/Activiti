package de.gravitex.bpmn.delegate;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.gravitex.bpmn.message.ChangeProcessMessages;

public class CancelChangeCustomerDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		BpmPlatform.getDefaultProcessEngine().getRuntimeService().correlateMessage(ChangeProcessMessages.MESSAGE_CHANGE_CANCELLED_CUSTOMER);
	}
}
