package com.example.auro.lib.services.impls;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.auro.lib.services.interfaces.CounterService;

@Service
public class CounterServiceImpl implements CounterService {
	
	private static final AtomicLong counter = new AtomicLong(10000L);

	@Override
	public Long getNextCounterNumber() {
		return counter.getAndIncrement();
	}

}
