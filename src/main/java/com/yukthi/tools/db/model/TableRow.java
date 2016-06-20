package com.yukthi.tools.db.model;

import java.io.Serializable;

/**
 * The serializable class TableRow to store the table records in the file.
 * 
 * @author Pritam
 */
public class TableRow implements Serializable
{
	/**
	 * The Serial Version UID.
	 */
	private static final long serialVersionUID = 3622063228333563344L;

	/**
	 * The table name.
	 **/
	private String tableName;

	/**
	 * The row values.
	 **/
	private Object rowValues[];

	/**
	 * Gets the row values.
	 *
	 * @return the row values
	 */
	public Object[] getRowValues()
	{
		return rowValues;
	}

	/**
	 * Sets the row values.
	 *
	 * @param rowValues
	 *            the new row values
	 */
	public void setRowValues(Object[] rowValues)
	{
		this.rowValues = rowValues;
	}

	/**
	 * Gets the table name.
	 *
	 * @return the table name
	 */
	public String getTableName()
	{
		return tableName;
	}

	/**
	 * Sets the table name.
	 *
	 * @param tableName
	 *            the new table name
	 */
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}
}
