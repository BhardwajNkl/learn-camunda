package com.bhardwaj.workflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhardwaj.workflow.Migration;
import com.bhardwaj.workflow.model.MigrationDto;

@RestController
@RequestMapping("/api/migrate")
public class MigrationController {
	@Autowired
	private Migration migration;
	
	@PostMapping()
	public String migrate(@RequestBody MigrationDto migrationDto) {
		try {
			List<String> processInstanceIds = migrationDto.getProcessInstanceIds();
			this.migration.migrateInstances(processInstanceIds);
			return "migration successful";
		} catch (Exception exception) {
			exception.printStackTrace();
			return "EXCEPTION"+exception.getMessage();
		}
	}
}
