package com.bhardwaj.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhardwaj.workflow.entities.LeaveApplicationEntity;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplicationEntity, Integer> {
	LeaveApplicationEntity findByProcessInstanceId(String processInstanceId);
}
