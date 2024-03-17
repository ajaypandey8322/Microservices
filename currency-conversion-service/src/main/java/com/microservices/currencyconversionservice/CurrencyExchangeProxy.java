package com.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="currency-exchange", url="localhost:8000" )// name of applciation we were created 
@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {
	

	@GetMapping("/currency-exchange/from/{from}/to/{to}")///currency-exchange/from/USD/to/INR
	public CurrencyConversion retriveExchangeValue(@PathVariable String from, @PathVariable String to);
	
}
