package com.gsk.santander.mktpxhandler.controller;

import java.util.NoSuchElementException;

import com.gsk.santander.mktpxhandler.manager.QuoteDataManager;
import com.gsk.santander.mktpxhandler.manager.QuoteDataManagerFactory;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("getPrices")
public class QuoteDataRestController {

	private QuoteDataManager quoteDataManager = QuoteDataManagerFactory
			.getInstance().getLevelOneQuoteDataManager();

	@GET
	@Path("getBid/{name}")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
	public double getBidPrice(@PathParam("name") String instrumentName) throws NoSuchElementException{
		if(instrumentName == null || instrumentName.trim().isEmpty()) {
			throw new NoSuchElementException();
		}
		return quoteDataManager.getBidPrice(instrumentName.trim().toUpperCase());
	}
	
	@GET
	@Path("getAsk/{name}")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
	public double getAskPrice(@PathParam("name") String instrumentName) throws NoSuchElementException{
		if(instrumentName == null || instrumentName.trim().isEmpty()) {
			throw new NoSuchElementException();
		}
		return quoteDataManager.getAskPrice(instrumentName.trim().toUpperCase());
	}
}
