package com.bhardwaj.workflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhardwaj.workflow.entity.BookOrder;
import com.bhardwaj.workflow.entity.DeliverableOrNotDto;
import com.bhardwaj.workflow.entity.NewOrderDto;
import com.bhardwaj.workflow.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@PostMapping()
	public ResponseEntity<?> newOrder(@Valid @RequestBody NewOrderDto newOrderDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body("order not placed! please provide all required details.");
		}
		
		try {
			this.orderService.newOrder(newOrderDto);
			return ResponseEntity.ok().body(null);
			
		} catch (Exception exception) {
			return ResponseEntity.internalServerError().body("something went wrong! please try again.");
		}
	}
	
	@PostMapping("/cancel/{orderId}")
	public ResponseEntity<?> cancelOrder(@PathVariable("orderId") int orderId) {
		try{
			this.orderService.cancelOrder(orderId);
			return ResponseEntity.ok(null);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<?> getAllOrders() {
		List<BookOrder> orders = this.orderService.getAllOrders();
		return ResponseEntity.ok().body(orders);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable("orderId") int orderId) {
		try{
			BookOrder order = this.orderService.getOrderById(orderId);
			return ResponseEntity.ok().body(order);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
	
	@PostMapping("/deliverable")
	public ResponseEntity<?> deliverableOrNot(@RequestBody DeliverableOrNotDto deliverableOrNotDto) {
		try{
			this.orderService.deliverableOrNot(deliverableOrNotDto);
			return ResponseEntity.ok(null);
		} catch (Exception exception) {
			return ResponseEntity.internalServerError().body(exception.getMessage());
		}
	}
	
	@PostMapping("/ship/{orderId}")
	public void shipBook(@PathVariable("orderId") int orderId) {
		this.orderService.shipBook(orderId);
	}
}
