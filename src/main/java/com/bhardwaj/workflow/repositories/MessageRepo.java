package com.bhardwaj.workflow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bhardwaj.workflow.entities.Message;
@Repository
public interface MessageRepo extends JpaRepository<Message, Integer> {

}
