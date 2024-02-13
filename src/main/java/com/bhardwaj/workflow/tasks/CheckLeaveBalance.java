package com.bhardwaj.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class CheckLeaveBalance implements JavaDelegate {
	private int leaveCount = 3;
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("checking leave availability...");
		String emp_id = execution.getVariable("emp_id").toString();
		int leaves_requested = Integer.parseInt(execution.getVariable("leaves_requested").toString());
		System.out.println("employee id: "+emp_id);
		System.out.println("Available Leaves: "+leaveCount+" and Requested Leaves: "+leaves_requested);
//		if(leaves_requested<=leaveCount) {
//			leaveCount-=leaves_requested;
//		}
		execution.setVariable("available_leave", (leaveCount-leaves_requested));
	}
}
