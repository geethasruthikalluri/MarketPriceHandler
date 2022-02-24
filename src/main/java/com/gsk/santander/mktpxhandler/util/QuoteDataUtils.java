package com.gsk.santander.mktpxhandler.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.gsk.santander.mktpxhandler.bean.QuoteInfoBean;
import com.gsk.santander.mktpxhandler.exception.ParseFailureException;

/**
 * Utility class for various common operations
 * @author gkalluri
 *
 */
public class QuoteDataUtils {

	private static final int DOUBLE_MAX_DECIMALS = 17;
	public static final double EPSILON = 1E-8;
	private static final String COMMA_SEPARATOR = ",";
	public static final String LINE_SEPARATOR = System.lineSeparator();

	/**
	 * Use this function for multiplication of doubles using bigdecimals
	 */
	public static Double multiplyAsBigDecimal(double value1, double value2) {
		BigDecimal bigDecimal1 = BigDecimal.valueOf(value1);
		BigDecimal bigDecimal2 = BigDecimal.valueOf(value2);
		return bigDecimal1.multiply(bigDecimal2).doubleValue();
	}

	/**
	 * Use this function for division of doubles using bigdecimals
	 */
	public static Double divideAsBigDecimal(double value1, double value2) {
		if (Math.abs(value2 - 0) < EPSILON) {
			return 0.0;
		}
		BigDecimal bigDecimal1 = BigDecimal.valueOf(value1);
		BigDecimal bigDecimal2 = BigDecimal.valueOf(value2);
		return bigDecimal1.divide(bigDecimal2, DOUBLE_MAX_DECIMALS, RoundingMode.HALF_EVEN).doubleValue();
	}

	/**
	 * parse the string from market data provider and construct quote bean
	 * @param quoteFeedMsg
	 * @return
	 * @throws ParseFailureException
	 */
	public static QuoteInfoBean parse(String quoteFeedMsg) throws ParseFailureException {
		if (quoteFeedMsg == null || quoteFeedMsg.trim().isEmpty()) {
			throw new ParseFailureException("Null or empty message", quoteFeedMsg, null);
		}
		try {
			String[] messageParts = quoteFeedMsg.split(COMMA_SEPARATOR);
			if (messageParts.length != 5) {
				throw new ParseFailureException("Message did not cotain all required data", quoteFeedMsg, null);
			}
			String id = messageParts[MessageStructure.ID.getIndex()].trim();
			String instrument = messageParts[MessageStructure.INSTRUMENT.getIndex()].
					trim().toUpperCase();
			String bidString = messageParts[MessageStructure.BID.getIndex()].trim();
			double bidPx = Double.valueOf(bidString);
			String askString = messageParts[MessageStructure.ASK.getIndex()].trim();
			double askPx = Double.valueOf(askString);
			String timestamp = messageParts[MessageStructure.TIMESTAMP.getIndex()].trim();
			return getQuoteInfoBean(id, instrument, bidPx, askPx, timestamp);
		} catch (Exception e) {
			throw new ParseFailureException("Error parsing inbound message", quoteFeedMsg, e);
		}
	}
	
	public static double add(double a, double b) {
		return BigDecimal.valueOf(a).add(BigDecimal.valueOf(b)).doubleValue();
	}

	public static double subtract(double a, double b) {
		return BigDecimal.valueOf(a).subtract(BigDecimal.valueOf(b)).doubleValue();
	}

	private static QuoteInfoBean getQuoteInfoBean(String id, String instrument, double bid, double ask,
			String timestamp) {
		QuoteInfoBean quoteInfoBean = new QuoteInfoBean(instrument);
		quoteInfoBean.setUniqueId(id);
		quoteInfoBean.setBidPx(bid);
		quoteInfoBean.setAskPx(ask);
		quoteInfoBean.setTimeStamp(timestamp);
		return quoteInfoBean;
	}

	private enum MessageStructure {
		ID(0), INSTRUMENT(1), BID(2), ASK(3), TIMESTAMP(4);

		private final int index;

		MessageStructure(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}
}
