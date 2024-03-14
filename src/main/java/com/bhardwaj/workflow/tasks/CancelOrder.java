package com.bhardwaj.workflow.tasks;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhardwaj.workflow.entity.BookOrder;
import com.bhardwaj.workflow.repository.BookOrderRepository;
@Service
public class CancelOrder implements JavaDelegate {

	@Autowired
	private BookOrderRepository bookOrderRepository;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		int orderId = (Integer) execution.getVariable("orderId");
		// get order by order id
		BookOrder bookOrder = this.bookOrderRepository.findById(orderId)
				.orElseThrow(()->new RuntimeException("order not found!"));
		
		// order can be cancelled only if the book is not yet shipped
		if(!bookOrder.getStatus().equalsIgnoreCase("DISPATCHED")) {
			// cancel. first of all terminate the process instance
			String processInstanceId = bookOrder.getProcessInstanceId();
			this.runtimeService.deleteProcessInstance(processInstanceId, "order cancelled");
			bookOrder.setStatus("FINISHED_CANCELLED");
			bookOrderRepository.save(bookOrder);
		} else {
			throw new RuntimeException("Book has been dispatched! Cannot cancel.");
		}
	}

}
