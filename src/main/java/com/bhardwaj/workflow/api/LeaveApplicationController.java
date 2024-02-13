package com.bhardwaj.workflow.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhardwaj.workflow.dto.LeaveApplicationDto;
import com.bhardwaj.workflow.dto.ManagerApprovalDto;
import com.bhardwaj.workflow.entities.LeaveApplicationEntity;
import com.bhardwaj.workflow.services.LeaveApplicationService;

@RestController
@RequestMapping("/api/leave")
@CrossOrigin("*")
public class LeaveApplicationController {
	
	@Autowired
	private LeaveApplicationService leaveApplicationService;
	
	@PostMapping("/apply")
	public LeaveApplicationEntity apply(@RequestBody LeaveApplicationDto leaveApplicationDto) {
		return this.leaveApplicationService.apply(leaveApplicationDto);
	}
	
	@GetMapping("/all")
	public List<LeaveApplicationEntity> getleaveApplicationList() {
		return this.leaveApplicationService.getleaveApplicationList();
	}
	
	@PutMapping("/manager-approval")
	public void approveLeave(@RequestBody ManagerApprovalDto managerApprovalDto) {
		this.leaveApplicationService.approveLeave(managerApprovalDto);
	}
	
	@GetMapping("/status/{leaveId}")
	public String getLeaveApplicationStatus(@PathVariable int leaveId) {
		return this.leaveApplicationService.getLeaveApplicationStatus(leaveId);
	}
}
