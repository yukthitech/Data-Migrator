package com.yukthi.tools.db.exception;

/**
 * The Class MigrationException.
 */
@SuppressWarnings("serial")
public class MigrationException extends RuntimeException
{
	/**
	 * Instantiates a new migration exception.
	 */
	public MigrationException()
	{}

	/**
	 * Instantiates a new migration exception.
	 *
	 * @param message
	 *            the message
	 */
	public MigrationException(String message)
	{
		super(message);
	}

	/**
	 * Instantiates a new migration exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public MigrationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Instantiates a new migration exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public MigrationException(Throwable cause)
	{
		super(cause);
	}
}
