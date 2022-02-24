package com.gsk.santander.mktpxhandler.bean;

/**
 * Bean class which contains all symbol quote data
 * @author gkalluri
 *
 */
public class QuoteInfoBean {
	private String uniqueId;
	private String symbol;
	private double bidPx;
	private double askPx;
	private String timeStamp;
	
	public QuoteInfoBean(String symbol) {
		this.symbol = symbol;
	}
	
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getSymbol() {
		return symbol;
	}
	public double getBidPx() {
		return bidPx;
	}
	public void setBidPx(double bidPx) {
		this.bidPx = bidPx;
	}
	public double getAskPx() {
		return askPx;
	}
	public void setAskPx(double askPx) {
		this.askPx = askPx;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
