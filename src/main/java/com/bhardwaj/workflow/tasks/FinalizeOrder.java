package com.bhardwaj.workflow.tasks;

import java.util.Random;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
@Service
public class FinalizeOrder implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Random rand = new Random();
		// random food price. [50,100]
		int foodPrice = rand.nextInt(51) + 50;
		
		// random available balance
		int availableMoney = rand.nextInt(51) + 50;
		
		System.out.println("food price: "+foodPrice+". Available money: "+availableMoney);
		
		if (foodPrice > availableMoney) {
			System.out.println("Cannot finalize order! Insufficient balance.");
			throw new BpmnError("400");
			
        } else {
        	System.out.println("ordered");
        }
		
	}

}
