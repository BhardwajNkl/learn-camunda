package com.bhardwaj.workflow.services;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhardwaj.workflow.dto.LeaveApplicationDto;
import com.bhardwaj.workflow.dto.ManagerApprovalDto;
import com.bhardwaj.workflow.entities.LeaveApplicationEntity;
import com.bhardwaj.workflow.repository.LeaveApplicationRepository;

@Service
public class LeaveApplicationService {
	@Autowired
	 private RuntimeService runtimeService;
	@Autowired
	 private TaskService taskService;
	@Autowired
	LeaveApplicationRepository leaveApplicationRepository;
	
	public LeaveApplicationEntity apply(LeaveApplicationDto leaveApplicationDto) {
		
		// start process
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("LEAVE_PROCESS");
		String processInstanceId = processInstance.getId();
		
		
		// save in database table
        LeaveApplicationEntity leaveApplicationEntity = new LeaveApplicationEntity();
        leaveApplicationEntity.setEmpId(leaveApplicationDto.getEmpId());
        leaveApplicationEntity.setLeavesRequested(leaveApplicationDto.getLeavesRequested());
        leaveApplicationEntity.setProcessInstanceId(processInstanceId);
        leaveApplicationEntity.setStatus("PENDING");
        leaveApplicationRepository.save(leaveApplicationEntity);
		// complete apply user task
		Task applyTask = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskDefinitionKey("Apply_Leave")
                .singleResult();

        if (applyTask != null) {
            taskService.complete(applyTask.getId(),
            		Map.of("emp_id", leaveApplicationDto.getEmpId(),
            				"leaves_requested", leaveApplicationDto.getLeavesRequested()));
        }
        
        
        return leaveApplicationEntity;		
	}
	
	public List<LeaveApplicationEntity> getleaveApplicationList() {
		// load from table
		return leaveApplicationRepository.findAll();
	}
	
	public void approveLeave(ManagerApprovalDto managerApprovalDto) {
		// complete manager_approval user task
		LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository
				.findById(managerApprovalDto.getLeaveId()).orElse(null);
		Task managerApprovalTask = taskService.createTaskQuery()
                .processInstanceId(leaveApplicationEntity.getProcessInstanceId())
                .taskDefinitionKey("Manager_Approval")
                .singleResult();
        if (managerApprovalTask != null) {
            taskService.complete(managerApprovalTask.getId(),
            		Map.of("approval_status",managerApprovalDto.getApprovalStatus()));
        }		
	}
	
	public String getLeaveApplicationStatus(int leaveId) {
		// load from table
		LeaveApplicationEntity leaveApplicationEntity = leaveApplicationRepository.findById(leaveId).orElse(null);
		if(leaveApplicationEntity != null) {
			return leaveApplicationEntity.getStatus();
		}
		
		return null;
	}
}
