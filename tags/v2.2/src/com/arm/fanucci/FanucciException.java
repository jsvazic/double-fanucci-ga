package com.arm.fanucci;

/**
 * Default exception class for failures within the application.
 * 
 * @author jsvazic
 *
 */
public class FanucciException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public FanucciException() {
	}

	/**
	 * Default constructor.
	 * 
	 * @param msg The message for the exception.
	 */
	public FanucciException(String msg) {
		super(msg);
	}

	/**
	 * Default constructor.
	 * 
	 * @param ex The linked exception.
	 */
	public FanucciException(Throwable ex) {
		super(ex);
	}

	/**
	 * Default constructor.
	 * 
	 * @param msg The message for the exception.
	 * @param ex The linked exception.
	 */
	public FanucciException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
