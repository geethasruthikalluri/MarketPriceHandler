package com.gsk.santander.mktpxhandler.adapter;

import com.gsk.santander.mktpxhandler.bean.QuoteInfoBean;
import com.gsk.santander.mktpxhandler.exception.ParseFailureException;
import com.gsk.santander.mktpxhandler.manager.QuoteDataManager;
import com.gsk.santander.mktpxhandler.manager.QuoteDataManagerFactory;
import com.gsk.santander.mktpxhandler.util.QuoteDataUpdaterService;
import com.gsk.santander.mktpxhandler.util.QuoteDataUtils;

/**
 * This class is responsible to register to market data and get data.
 * @author gkalluri
 *
 */
public class LevelOneQuoteDataAdapter implements IQuoteDataAdapter {

	private QuoteDataUpdaterService service = new QuoteDataUpdaterService();
	
	/**
	 * When quotedata is received, modify prices according to commissions
	 * and store in cache
	 */
	@Override
	public void onMessage(String message) {
		//when a message is received
		System.out.println("Feed received: "+message);
		String quoteFeed=new String(message);
		String[] quoteFeedArr = quoteFeed.split(QuoteDataUtils.LINE_SEPARATOR);
		QuoteDataManager quoteDataManager = QuoteDataManagerFactory
				.getInstance().getLevelOneQuoteDataManager();
		if(quoteFeedArr != null && quoteFeedArr.length > 0) {
			for(String quoteFeedMsg : quoteFeedArr) {
				try {
					QuoteInfoBean bean = QuoteDataUtils.parse(quoteFeedMsg);
					service.applyCommissions(bean);
					quoteDataManager.add(bean.getSymbol(), bean);
				} catch(ParseFailureException exception) {
					System.out.println("Exception occurred :" + exception);
				}
			}
		}
	}
	
	@Override
	public void register() {
		// Register with quote data provider
	}
	
	@Override
	public void unregister() {
		// unregister with quote data provider
	}
}
