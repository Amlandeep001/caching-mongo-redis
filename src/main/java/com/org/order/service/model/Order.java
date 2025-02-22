package com.org.order.service.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class Order implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String productId;
	private LocalDate date;
}