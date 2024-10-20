package com.org.order.service.advice;

import java.time.Duration;
import java.time.Instant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * Advice for order service application to capture the duration for @TrackExecutionTime annotated method calls
 * @author Amlan
 *
 */
@Aspect
@Component
@Log4j2
public class ExecutionTimeTrackerAdvice
{
	@Around("@annotation(TrackExecutionTime)")
	public Object trackTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
	{
		String methodName = proceedingJoinPoint.getSignature().getName();
		String className = proceedingJoinPoint.getTarget().getClass().toString();

		final Instant startTime = Instant.now();
		log.info("Before method invoked: {} from className: {} where current time :: {}", methodName, className, startTime);

		Object object = proceedingJoinPoint.proceed();

		final Instant endTime = Instant.now();
		Long duration = Duration.between(startTime, endTime).toMillis();
		log.info("After method invoked: {} from className: {} where current time :: {} and duration :: {} ms", methodName, className, endTime, duration);

		return object;
	}
}
