package com.bhardwaj.workflow.service;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhardwaj.workflow.entity.BookOrder;
import com.bhardwaj.workflow.entity.DeliverableOrNotDto;
import com.bhardwaj.workflow.entity.NewOrderDto;
import com.bhardwaj.workflow.repository.BookOrderRepository;

@Service
public class OrderService {
	@Autowired
	private BookOrderRepository orderRepository;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	public void newOrder(NewOrderDto newOrderDto) {
		
		try {
			ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage("bookOrderReceived",
					Map.of("customerName", newOrderDto.getCustomerName(),
							"customerMobile",newOrderDto.getCustomerMobile(),
							"address",newOrderDto.getAddress(),
							"pinCode",newOrderDto.getPinCode(),
							"bookName",newOrderDto.getBookName())
					);
			
			 String processInstanceId = processInstance.getId();
			 
			 BookOrder order = new BookOrder();
				order.setProcessInstanceId(processInstanceId);
				order.setCustomerName(newOrderDto.getCustomerName());
				order.setCustomerMobile(newOrderDto.getCustomerMobile());
				order.setAddress(newOrderDto.getAddress());
				order.setPinCode(newOrderDto.getPinCode());
				order.setBookName(newOrderDto.getBookName());
				order.setStatus("PENDING_DELIVERABILITY_CHECK");			
				this.orderRepository.save(order);
						
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
		
	}
	
	public void cancelOrder(int orderId) {
		try{
			runtimeService.createSignalEvent("cancelBookOrder")
			.setVariables(Map.of("orderId",orderId)).send();
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
		
	}
	
	public List<BookOrder> getAllOrders() {
		return this.orderRepository.findAll();
	}
	
	public BookOrder getOrderById(int orderId) {
		return this.orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("order not found!"));
	}
	
	public void deliverableOrNot(DeliverableOrNotDto deliverableOrNotDto) {
		// get the order by id
		int orderId = deliverableOrNotDto.getOrderId();
		BookOrder bookOrder = this.orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("order not found!"));
			// get the process instance id
		String processInstanceId = bookOrder.getProcessInstanceId();
		
		try {
			Task task = this.taskService.createTaskQuery()
					.processInstanceId(processInstanceId)
					.taskDefinitionKey("isDeliveryPossible")
					.singleResult();
			if(task!=null) {
				this.taskService.complete(task.getId(),
						Map.of("canDeliver",deliverableOrNotDto.isCanDeliver())
						);
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
			
	}
	
	public void shipBook(int orderId) {
		// get the order by id
		BookOrder bookOrder = this.orderRepository.findById(orderId)
				.orElseThrow(()-> new RuntimeException("order not found"));
			// get the process instance id
		String processInstanceId = bookOrder.getProcessInstanceId();
		
		try {
			Task task = this.taskService.createTaskQuery()
					.processInstanceId(processInstanceId)
					.taskDefinitionKey("shipBook")
					.singleResult();
			if(task!=null) {
				this.taskService.complete(task.getId());
				bookOrder.setStatus("DISPATCHED");
				this.orderRepository.save(bookOrder);
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
	}
	
	public void updateStatus(String processInstanceId, String status) {
		BookOrder bookOrder = this.orderRepository.findByProcessInstanceId(processInstanceId)
				.orElseThrow(()->new RuntimeException("order not found"));
		bookOrder.setStatus(status);
		this.orderRepository.save(bookOrder);
	}
}
