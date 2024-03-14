package com.bhardwaj.workflow.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeliverableOrNotDto {
	private int orderId;
	private boolean canDeliver;
}
