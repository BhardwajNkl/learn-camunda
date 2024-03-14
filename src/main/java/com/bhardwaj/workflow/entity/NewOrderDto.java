package com.bhardwaj.workflow.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewOrderDto {
	@NotNull
	private String customerName;
	@NotNull
	private String customerMobile;
	@NotNull
	private String address;
	@NotNull
	private String pinCode;
	@NotNull
	private String bookName;
}
