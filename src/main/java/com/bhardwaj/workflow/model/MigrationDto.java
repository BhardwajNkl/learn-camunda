package com.bhardwaj.workflow.model;

import java.util.List;

public class MigrationDto {
	List<String> processInstanceIds;
	
	public MigrationDto() {
		super();
	}
	
	public MigrationDto(List<String> processInstanceIds) {
		this.processInstanceIds = processInstanceIds;
	}

	public List<String> getProcessInstanceIds() {
		return processInstanceIds;
	}

	public void setProcessInstanceIds(List<String> processInstanceIds) {
		this.processInstanceIds = processInstanceIds;
	}
}
