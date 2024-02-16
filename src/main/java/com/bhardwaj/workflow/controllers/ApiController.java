package com.bhardwaj.workflow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhardwaj.workflow.entities.Message;
import com.bhardwaj.workflow.entities.Reply;
import com.bhardwaj.workflow.services.ApiService;

import jakarta.ws.rs.Path;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ApiController {
	@Autowired
	private ApiService apiService;
	
	@PostMapping("/send-message")
	public boolean sendMessage(@RequestBody Message message) {
		this.apiService.sendMessage(message);
		return true;
	}
	
	@GetMapping("/get-message-list")
	public List<Message> getMessages(){
		return this.apiService.getMessages();
	}
	
	@PostMapping("/like/{messageId}")
	public void likeMessage(@PathVariable int messageId) {
		this.apiService.likeMessage(messageId);
	}
	
	@PostMapping("/dislike/{messageId}")
	public void dislikeMessage(@PathVariable int messageId) {
		this.apiService.dislikeMessage(messageId);
	}
	@PostMapping("/reply/message/{messageId}")
	public void reply(@PathVariable int messageId, @RequestBody Reply reply) {
		this.apiService.reply(messageId, reply);
	}
}
