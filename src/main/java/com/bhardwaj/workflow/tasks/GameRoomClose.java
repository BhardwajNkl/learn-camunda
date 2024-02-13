package com.bhardwaj.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GameRoomClose implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("Game room is locked successfully");
		
	}

}
