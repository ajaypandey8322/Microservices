package com.microservices.currencyexchangeservices;

import org.springframework.data.jpa.repository.JpaRepository;

//coonnect database and get data through rest api
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
	CurrencyExchange findByFromAndTo(String from, String to);
}
