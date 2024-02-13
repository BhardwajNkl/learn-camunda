package com.bhardwaj.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhardwaj.workflow.entities.LeaveApplicationEntity;
import com.bhardwaj.workflow.repository.LeaveApplicationRepository;

@Service
public class ApplicationRejected implements JavaDelegate {
	@Autowired
	private LeaveApplicationRepository leaveApplicationRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Your leave application has been rejected.");
		// get process instance id
		String processInstanceId = execution.getProcessInstanceId();
		// update in database
		// get the leave application entity using process instance id
		LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findByProcessInstanceId(processInstanceId);
		leaveApplicationEntity.setStatus("REJECTED");
		leaveApplicationRepository.save(leaveApplicationEntity);
	}

}
