package com.org.order.service.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.order.service.advice.TrackExecutionTime;
import com.org.order.service.model.Order;
import com.org.order.service.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController
{
	private final OrderService orderService;

	// Retrieve an order by ID
	@GetMapping("/{id}")
	@TrackExecutionTime
	public ResponseEntity<Order> getOrderById(@PathVariable String id) throws Exception
	{
		return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
	}

	// Retrieve all orders
	@GetMapping("/all")
	@TrackExecutionTime
	public ResponseEntity<List<Order>> getOrders() throws Exception
	{
		List<Order> orders = orderService.getOrders(); // Get the orders from the service
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	// Create a new order
	@PostMapping("/create")
	@TrackExecutionTime
	public ResponseEntity<String> createOrder(@RequestBody Order order)
	{
		return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
	}

	// Update an existing order
	@PutMapping("/update/{id}")
	@TrackExecutionTime
	public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order updatedOrder) throws Exception
	{
		Order order = orderService.updateOrder(id, updatedOrder);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	// Delete an order
	@DeleteMapping("/delete/{id}")
	@TrackExecutionTime
	public ResponseEntity<Void> deleteOrder(@PathVariable String id) throws Exception
	{
		orderService.deleteOrder(id); // Deletes the order from the repository
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returns a 204 No Content response
	}
}
