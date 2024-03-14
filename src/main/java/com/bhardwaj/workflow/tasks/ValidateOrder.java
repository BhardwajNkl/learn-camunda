package com.bhardwaj.workflow.tasks;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ValidateOrder implements JavaDelegate {
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		try {
			// get passed variables
			String customerName = (String) execution.getVariable("customerName");
			String customerMobile = (String) execution.getVariable("customerMobile");
			String address = (String) execution.getVariable("address");
			String pinCode = (String) execution.getVariable("pinCode");
			String bookName = (String) execution.getVariable("bookName");
			
			// validate these inputs.
			
			// 1. for now, we assume everything is valid if it is not null.
			if(customerName==null || customerMobile==null || address==null || pinCode==null || bookName==null) {
				System.out.println("Invalid order request.");
				throw new BpmnError("invalidOrder");
			}			
			
		} catch (Exception exception) {
			// re-throw BPMN error
			throw exception;
		}
	}

}
