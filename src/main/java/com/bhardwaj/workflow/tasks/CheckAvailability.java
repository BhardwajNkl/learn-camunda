package com.bhardwaj.workflow.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhardwaj.workflow.repository.BookRepository;
import com.bhardwaj.workflow.service.OrderService;
@Service
public class CheckAvailability implements JavaDelegate {
	@Autowired
	private OrderService orderService;
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// check if the book is available. currently we are using a hard-coded set of books only.
		String bookName = (String) execution.getVariable("bookName");
		if(!bookRepository.isAvailable(bookName)) {
			execution.setVariable("isAvailable", false);
		}
		else {
			orderService.updateStatus(execution.getProcessInstanceId(), "PENDING_SHIPPING");
			execution.setVariable("isAvailable", true);
		}
	}

}
