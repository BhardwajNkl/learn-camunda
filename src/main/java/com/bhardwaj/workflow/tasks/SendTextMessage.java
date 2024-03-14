package com.bhardwaj.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendTextMessage implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String mobile = (String) execution.getVariable("customerMobile");
		String messageType = (String) execution.getVariable("messageType");
		System.out.println("sent text message To: "+mobile+", Message: "+messageType);
	}

}
