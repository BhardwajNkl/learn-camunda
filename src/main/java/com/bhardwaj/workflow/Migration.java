package com.bhardwaj.workflow;

import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.migration.MigrationPlan;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class Migration {
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private ProcessEngine processEngine;
	
	private String getProcessDefinitionByVersion(String processKey, int version) {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinitionQuery pdQuery = repositoryService.createProcessDefinitionQuery()
		    .processDefinitionKey(processKey)
		    .processDefinitionVersion(version);

		ProcessDefinition processDefinition = pdQuery.singleResult();
		String processDefinitionId = processDefinition.getId();
		
		return processDefinitionId;
	}
	
	private MigrationPlan createMigrationPlan(String source, String target) {
		MigrationPlan migrationPlan = runtimeService
		  .createMigrationPlan(source, target)
		  .mapEqualActivities()
//		  .mapActivities("goToWaitingRoom", "goToDoctor")
		  .build();
		
		return migrationPlan;
	}
	
	public void migrateInstances(List<String> processInstanceIds) {
		// get source and target versions
		String source = this.getProcessDefinitionByVersion("demo_process", 1);
		String target = this.getProcessDefinitionByVersion("demo_process", 2);
		
		// create migration plan
		MigrationPlan migrationPlan = this.createMigrationPlan(source, target);
		
		// migrate the given list of process instances
		runtimeService.newMigration(migrationPlan)
		  .processInstanceIds(processInstanceIds)
		  .execute();
	}
}
