package com.gsk.santander.mktpxhandler.manager;

/**
 * Factory class to get quotedatamanager
 * @author gkalluri
 *
 */
public class QuoteDataManagerFactory {

	private static class SingletonHelper {
		private static final QuoteDataManagerFactory INSTANCE = new QuoteDataManagerFactory();
	}

	private QuoteDataManagerFactory() {
	}

	public static synchronized QuoteDataManagerFactory getInstance() {
		return SingletonHelper.INSTANCE;
	}

	private QuoteDataManager levelOneQuoteDataManager;

	public synchronized QuoteDataManager getLevelOneQuoteDataManager() {
		if (levelOneQuoteDataManager == null) {
			levelOneQuoteDataManager = new QuoteDataManager();
		}
		return levelOneQuoteDataManager;
	}
}
