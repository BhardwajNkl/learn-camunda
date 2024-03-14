package com.bhardwaj.workflow.repository;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
	private HashSet<String> booksAvaialable;
	public BookRepository() {
		booksAvaialable = new HashSet<>();
		booksAvaialable.add("New Machine");
		booksAvaialable.add("White Girl");
		booksAvaialable.add("Java Book");
	}
	
	public boolean isAvailable(String bookName) {
		return this.booksAvaialable.contains(bookName);
	}
	
	public List<String> getAvailableBooks(){
		return this.booksAvaialable.stream().toList();
	}
}
