package com.org.order.service.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.org.order.service.advice.TrackExecutionTime;
import com.org.order.service.model.Order;
import com.org.order.service.model.OrderRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderService
{
	public final OrderRepo orderRepo;

	// Retrieve an order by ID and cache the result
	@Cacheable(value = "order", key = "#id")
	@TrackExecutionTime
	public Order getOrderById(String id) throws Exception
	{
		log.info("Fetching getOrderById from MongoDB");
		return orderRepo.findById(id).orElseThrow(() -> new Exception("Order Not Found!"));
	}

	// Retrieve all orders and cache the result
	@Cacheable(value = "orders")
	@TrackExecutionTime
	public List<Order> getOrders() throws Exception
	{
		log.info("Fetching getOrders from MongoDB");
		return orderRepo.findAll();
	}

	// Create a new order and evict the cache for orders
	@CacheEvict(cacheNames = "orders", allEntries = true) // Clears all cached orders
	@TrackExecutionTime
	public String createOrder(Order order)
	{
		Order newOrder = Order.builder()
				.date(order.getDate())
				.productId(order.getProductId())
				.build();
		Order savedOrder = orderRepo.save(newOrder);
		return savedOrder.getId();
	}

	// Update an existing order and manage cache eviction
	@CacheEvict(cacheNames = {"order", "orders"},
			allEntries = true, key = "#id") // Evicts the specific order and clears all orders from the cache
	@TrackExecutionTime
	public Order updateOrder(String id, Order updatedOrder) throws Exception
	{
		Order existingOrder = orderRepo.findById(id)
				.orElseThrow(() -> new Exception("Order Not Found!"));

		existingOrder.setDate(updatedOrder.getDate());
		existingOrder.setProductId(updatedOrder.getProductId());
		return orderRepo.save(existingOrder);
	}

	// Delete an order and manage cache eviction
	@CacheEvict(cacheNames = {"order", "orders"},
			allEntries = true, key = "#id") // Evicts the specific order and clears all orders from the cache
	@TrackExecutionTime
	public void deleteOrder(String id) throws Exception
	{
		if(!orderRepo.existsById(id))
		{
			throw new Exception("Order Not Found!");
		}
		orderRepo.deleteById(id);
	}
}
