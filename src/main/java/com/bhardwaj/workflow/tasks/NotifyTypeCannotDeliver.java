package com.bhardwaj.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhardwaj.workflow.service.OrderService;
@Service
public class NotifyTypeCannotDeliver implements JavaDelegate {
	@Autowired
	private OrderService orderService;
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		orderService.updateStatus(execution.getProcessInstanceId(), "FINISHED_CANNOT_DELIVER");
		execution.setVariable("messageType", "cannot deliver");
	}
}
