package com.gsk.santander.mktpxhandler.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.gsk.santander.mktpxhandler.bean.QuoteInfoBean;

/**
 * This class is responsible to maintain symbol to quote data cache
 * @author gkalluri
 *
 */
public class QuoteDataManager {

	private Map<String, QuoteInfoBean> symbolToQuoteInfo = new HashMap<>();
	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private ReadLock readLock = readWriteLock.readLock();
	private WriteLock writeLock = readWriteLock.writeLock();
	
	public double getBidPrice(String symbol) throws NoSuchElementException{
		readLock.lock();
		try {
			verifySymbolAvailable(symbol);
			return symbolToQuoteInfo.get(symbol).getBidPx();
		} finally {
			readLock.unlock();
		}
	}
	
	public double getAskPrice(String symbol) throws NoSuchElementException{
		readLock.lock();
		try {
			verifySymbolAvailable(symbol);
			return symbolToQuoteInfo.get(symbol).getAskPx();
		} finally {
			readLock.unlock();
		}
	}
	
	public String getLastUpdated(String symbol) throws NoSuchElementException{
		readLock.lock();
		try {
			verifySymbolAvailable(symbol);
			return symbolToQuoteInfo.get(symbol).getTimeStamp();
		} finally {
			readLock.unlock();
		}
	}
	
	public void add(String symbol, QuoteInfoBean quoteInfoBean) {
		writeLock.lock();
		try {
			symbolToQuoteInfo.put(symbol, quoteInfoBean);
		} finally {
			writeLock.unlock();
		}
	}
	
	private void verifySymbolAvailable(String symbol) throws NoSuchElementException{
		if(!symbolToQuoteInfo.containsKey(symbol)) {
			throw new NoSuchElementException();
		}
	}
}
