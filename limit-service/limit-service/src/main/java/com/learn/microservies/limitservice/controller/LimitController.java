package com.learn.microservies.limitservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.microservies.limitservice.bean.Limits;
import com.learn.microservies.limitservice.configuration.Configurations;

@RestController
public class LimitController {
	
	@Autowired
	private Configurations configuration;
	
	@GetMapping("/limits")
	public Limits retriveLimits() {
		return new Limits(configuration.getMinimum(),configuration.getMaximum() );
		//return new Limits(1,1000);
	}
	
}
