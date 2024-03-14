package com.bhardwaj.workflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bhardwaj.workflow.entity.BookOrder;
@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Integer> {
	Optional<BookOrder> findByProcessInstanceId(String processInstanceId);
}
