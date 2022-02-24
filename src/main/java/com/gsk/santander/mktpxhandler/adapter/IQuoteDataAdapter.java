package com.gsk.santander.mktpxhandler.adapter;

public interface IQuoteDataAdapter {

	public void onMessage(String message);
	
	public void register();

	public void unregister();
}
