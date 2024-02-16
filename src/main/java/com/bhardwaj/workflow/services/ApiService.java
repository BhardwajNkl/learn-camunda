package com.bhardwaj.workflow.services;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhardwaj.workflow.entities.Message;
import com.bhardwaj.workflow.entities.Reply;
import com.bhardwaj.workflow.repositories.MessageRepo;
@Service
public class ApiService {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private MessageRepo messageRepo;
	
	public boolean sendMessage(Message message) {
		// 1. send message to engine to start the process
		try {
			// use sender name as business key for now. this key will be used to query the process instance id of the created process
			String businessKey = message.getSender();
			
			runtimeService.createMessageCorrelation("someoneDroppedAMessage")
			    .processInstanceBusinessKey(businessKey)
			    .setVariable("message", message.getMessageText())
			    .correlate();

			// 2. Retrieve the process instance using the business key
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
			    .processInstanceBusinessKey(businessKey)
			    .singleResult();
			
			String processInstanceId = processInstance.getProcessInstanceId();
			message.setProcessInstanceId(processInstanceId);
			
			// 3. save in db
			messageRepo.save(message);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	public List<Message> getMessages(){
		return this.messageRepo.findAll();
	}

	public void likeMessage(int messageId) {
		// 1. get message
		Message message = this.messageRepo.findById(messageId).orElse(null);
		if(message != null) {
			// 2. complete read message task. it is required.
			String processInstanceId = message.getProcessInstanceId();
			Task readMessageTask = taskService.createTaskQuery()
	                .processInstanceId(processInstanceId)
	                .taskDefinitionKey("readMessage")
	                .singleResult();
			if(readMessageTask != null) {
				taskService.complete(readMessageTask.getId());
			}
			
			// 3. like the message
			message.setResponseType("Liked");
			this.messageRepo.save(message);
			
			// 4. create a message for this like
			runtimeService.createMessageCorrelation("iLikeIt")
		    .correlate();
		}
	}
	
	
	public void dislikeMessage(int messageId) {
		// 1. get message
		Message message = this.messageRepo.findById(messageId).orElse(null);
		if(message != null) {
			// 2. complete read message task. it is required.
			String processInstanceId = message.getProcessInstanceId();
			Task readMessageTask = taskService.createTaskQuery()
	                .processInstanceId(processInstanceId)
	                .taskDefinitionKey("readMessage")
	                .singleResult();
			if(readMessageTask != null) {
				taskService.complete(readMessageTask.getId());
			}
			
			// 3. dislike the message
			message.setResponseType("Disliked");
			this.messageRepo.save(message);
			
			// 4. create a message for this like
			runtimeService.createMessageCorrelation("iDislikeIt")
		    .correlate();
		}
	}
	
	public void reply(int messageId, Reply reply) {
		// 1. get message
				Message message = this.messageRepo.findById(messageId).orElse(null);
				if(message != null) {
					// 2. complete read message task. it is required.
					String processInstanceId = message.getProcessInstanceId();
					Task readMessageTask = taskService.createTaskQuery()
			                .processInstanceId(processInstanceId)
			                .taskDefinitionKey("readMessage")
			                .singleResult();
					if(readMessageTask != null) {
						taskService.complete(readMessageTask.getId());
					}
					
					// 3. set response type as replied
					message.setResponseType("Replied");
					this.messageRepo.save(message);
					
					// 4. create a message event for this 'want to reply'
					runtimeService.createMessageCorrelation("iWantToReply")
				    .correlate();
					
					// 5. now, complete the reply task also
					Task sendReplyTask = taskService.createTaskQuery()
			                .processInstanceId(processInstanceId)
			                .taskDefinitionKey("sendReplyTask")
			                .singleResult();
					if(sendReplyTask != null) {
						// save the reply for this message
						message.setReply(reply.getReplyText());
						
						taskService.complete(sendReplyTask.getId());
					}
				}
	}
	
}
