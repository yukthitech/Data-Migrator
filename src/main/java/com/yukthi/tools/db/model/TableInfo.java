package com.yukthi.tools.db.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The serializable class TableInfo to store table information such as primary
 * key, unique keys, foreign keys.
 * 
 * @author Pritam
 */
public class TableInfo implements Serializable
{
	/**
	 * The Serial Version UID.
	 */
	private static final long serialVersionUID = 7045268545528567945L;

	/**
	 * The table name.
	 **/
	private String tableName;

	/**
	 * The column list.
	 **/
	private List<Column> columnList;

	/**
	 * The pk column name list.
	 **/
	private List<String> pkColumnNameList;

	/**
	 * The fk col nam prnt map.
	 **/
	private Map<String, String[]> fkColNamPrntMap;

	/**
	 * The constraint nam uk col nam map.
	 **/
	private Map<String, List<String>> constraintNamUKColNamMap;

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

	/**
	 * Gets the column list.
	 *
	 * @return the column list
	 */
	public List<Column> getColumnList()
	{
		return columnList;
	}

	/**
	 * Sets the column list.
	 *
	 * @param columnList
	 *            the new column list
	 */
	public void setColumnList(List<Column> columnList)
	{
		this.columnList = columnList;
	}

	/**
	 * Gets the pk column name list.
	 *
	 * @return the pk column name list
	 */
	public List<String> getPkColumnNameList()
	{
		return pkColumnNameList;
	}

	/**
	 * Sets the pk column name list.
	 *
	 * @param pkColumnNameList
	 *            the new pk column name list
	 */
	public void setPkColumnNameList(List<String> pkColumnNameList)
	{
		this.pkColumnNameList = pkColumnNameList;
	}

	/**
	 * Gets the fk col nam prnt map.
	 *
	 * @return the fk col nam prnt map
	 */
	public Map<String, String[]> getFkColNamPrntMap()
	{
		return fkColNamPrntMap;
	}

	/**
	 * Sets the fk col nam prnt map.
	 *
	 * @param fkColNamPrntMap
	 *            the fk col nam prnt map
	 */
	public void setFkColNamPrntMap(Map<String, String[]> fkColNamPrntMap)
	{
		this.fkColNamPrntMap = fkColNamPrntMap;
	}

	/**
	 * Gets the constraint nam uk col nam map.
	 *
	 * @return the constraint nam uk col nam map
	 */
	public Map<String, List<String>> getConstraintNamUKColNamMap()
	{
		return constraintNamUKColNamMap;
	}

	/**
	 * Sets the constraint nam uk col nam map.
	 *
	 * @param constraintNamUKColNamMap
	 *            the constraint nam uk col nam map
	 */
	public void setConstraintNamUKColNamMap(Map<String, List<String>> constraintNamUKColNamMap)
	{
		this.constraintNamUKColNamMap = constraintNamUKColNamMap;
	}
}