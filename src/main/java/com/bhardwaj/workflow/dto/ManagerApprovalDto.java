package com.bhardwaj.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagerApprovalDto {
	int leaveId;
	boolean approvalStatus;
	
	// lombok, for boolean values, does not use set or get prefixes. it works differently.
	// so defining own setter and getter
	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	public boolean getApprovalStatus() {
		return this.approvalStatus;
	}
	
}
