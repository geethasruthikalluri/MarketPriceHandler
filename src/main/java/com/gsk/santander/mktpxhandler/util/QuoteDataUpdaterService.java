package com.gsk.santander.mktpxhandler.util;

import com.gsk.santander.mktpxhandler.bean.QuoteInfoBean;

/**
 * This is used to update bid and ask prices adjusted with commissions
 * @author gkalluri
 *
 */
public class QuoteDataUpdaterService {

	 private double bidMarginInPct = -0.1d;
	 private double askMarginInPct = 0.1d;
	 
	public QuoteInfoBean applyCommissions(QuoteInfoBean quoteInfoBean) {
		double adjustedBid = adjustBidWithCommission(quoteInfoBean.getBidPx());
		double adjustedAsk = adjustAskWithCommission(quoteInfoBean.getAskPx());
		quoteInfoBean.setBidPx(adjustedBid);
		quoteInfoBean.setAskPx(adjustedAsk);
		return quoteInfoBean;
	}
	
	private double adjustBidWithCommission(double originalBid) {
		double commission = QuoteDataUtils.divideAsBigDecimal(
				QuoteDataUtils.multiplyAsBigDecimal(originalBid, bidMarginInPct), 100d);
		return QuoteDataUtils.add(originalBid, commission);
    }

	private double adjustAskWithCommission(double originalAsk) {
    	double commission = QuoteDataUtils.divideAsBigDecimal(
				QuoteDataUtils.multiplyAsBigDecimal(originalAsk, askMarginInPct), 100d);
    	return QuoteDataUtils.add(originalAsk, commission);
    }
    
    public double getBidMarginInPct() {
		return bidMarginInPct;
	}
    public void setBidMarginInPct(double bidMarginInPct) {
		this.bidMarginInPct = bidMarginInPct;
	}
    public double getAskMarginInPct() {
		return askMarginInPct;
	}
    public void setAskMarginInPct(double askMarginInPct) {
		this.askMarginInPct = askMarginInPct;
	}
}
