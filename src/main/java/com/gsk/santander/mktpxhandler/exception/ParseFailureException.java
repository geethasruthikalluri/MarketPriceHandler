package com.gsk.santander.mktpxhandler.exception;

/**
 * Exception to be thrown when message from market data provider
 * can not be parsed properly
 * @author gkalluri
 *
 */
public class ParseFailureException extends Exception {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String originalMessage;

	    public ParseFailureException(String message, String originalMessage, Throwable cause) {
	        super(message, cause);
	        this.originalMessage = originalMessage;
	    }

	    public String getOriginalMessage() {
	        return originalMessage;
	    }
}
