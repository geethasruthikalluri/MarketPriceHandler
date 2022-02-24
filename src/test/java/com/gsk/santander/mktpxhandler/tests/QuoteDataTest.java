package com.gsk.santander.mktpxhandler.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gsk.santander.mktpxhandler.adapter.LevelOneQuoteDataAdapter;
import com.gsk.santander.mktpxhandler.controller.QuoteDataRestController;

@RunWith(PowerMockRunner.class)
public class QuoteDataTest {
	
	private LevelOneQuoteDataAdapter adapter = new LevelOneQuoteDataAdapter();
	
	@Test
    public void checkPrices() {
		final double DELTA = 0.000000001;
    	String validMessage = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001";
    	String invalidMessage = "107, EUR/JPY, fds,119.90,01-06-2020 12:01:02:002";//invalid message
    	String messageWithMultipleLines = "109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100\r\n"+
    	"110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110";
    	String updatedMessage = "108, EUR/USD, 1.2500,1.2560,01-06-2020 12:01:02:002";

    	adapter.onMessage(validMessage);
    	
    	QuoteDataRestController controller = new QuoteDataRestController();
    	assertEquals(1.0989, controller.getBidPrice("EUR/USD"), DELTA);
    	assertEquals(1.2012, controller.getAskPrice("EUR/USD"), DELTA);
    	try {
    		controller.getAskPrice("GBP/USD");
    	} catch(NoSuchElementException exception) {
    		assertTrue(true);
    	}
    	try {
    		controller.getAskPrice("EUR/JPY");
    	} catch(NoSuchElementException exception) {
    		assertTrue(true);
    	}
    	
    	try {
    		controller.getAskPrice("invalidsymbol");
    	} catch(NoSuchElementException exception) {
    		assertTrue(true);
    	}
    	adapter.onMessage(messageWithMultipleLines);
    	assertEquals(1.2486501, controller.getBidPrice("GBP/USD"), DELTA);
    	assertEquals(1.2573561, controller.getAskPrice("GBP/USD"), DELTA);

    	assertEquals(119.49039, controller.getBidPrice("EUR/JPY"), DELTA);
    	assertEquals(120.02991, controller.getAskPrice("EUR/JPY"), DELTA);
    	
    	adapter.onMessage(invalidMessage);
    	assertEquals(119.49039, controller.getBidPrice("EUR/JPY"), DELTA);
    	assertEquals(120.02991, controller.getAskPrice("EUR/JPY"), DELTA);
    	
    	adapter.onMessage(updatedMessage);
    	assertEquals(1.24875, controller.getBidPrice("EUR/USD"), DELTA);
    	assertEquals(1.257256, controller.getAskPrice("EUR/USD"), DELTA);

    	assertEquals(1.24875, controller.getBidPrice("eur/usd"), DELTA);
    	try {
    		controller.getAskPrice("  ");
    	} catch(NoSuchElementException exception) {
    		assertTrue(true);
    	}
    	try {
    		controller.getAskPrice(null);
    	} catch(NoSuchElementException exception) {
    		assertTrue(true);
    	}
	}

}
