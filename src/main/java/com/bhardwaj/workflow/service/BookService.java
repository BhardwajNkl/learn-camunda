package com.bhardwaj.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhardwaj.workflow.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	public List<String> getAvaialableBooks(){
		return this.bookRepository.getAvailableBooks();
	}
}
