package com.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateConfiguration{
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
		
	}
}

@RestController
public class CurrencyConversionController {
	//http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from , @PathVariable String to , @PathVariable BigDecimal quantity) {
		
		HashMap<String,String>  uriVariable =new HashMap<>();
		uriVariable.put("from"	, from);
		uriVariable.put("to"	, to);
		ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, uriVariable );
		
		CurrencyConversion currencyConversion = responseEntity.getBody();
		return new CurrencyConversion(currencyConversion.getId(), from, to ,quantity 
				,currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple())
				,currencyConversion.getEnvironment()+" Rest Template");
	
	
	}
	
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from , @PathVariable String to , @PathVariable BigDecimal quantity) {
		
		
		CurrencyConversion currencyConversion = proxy.retriveExchangeValue(from, to);
		return new CurrencyConversion(currencyConversion.getId(), from, to ,quantity 
				,currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple())
				,currencyConversion.getEnvironment()+" Feign");
	
	
	
	}
		
}
