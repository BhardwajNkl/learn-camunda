package com.bhardwaj.workflow.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class LeaveApplicationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int leaveId;
	String empId;
	int leavesRequested;
	String processInstanceId;
	String status;
}
