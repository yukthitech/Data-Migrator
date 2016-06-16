package com.yukthi.tools.db.data.migration;

import java.util.List;
import java.util.Map;

import com.yukthi.tools.db.model.Column;

/**
 * The Class TableDetails. This class holds the extra information of the tables
 * 
 * @author Pritam
 */
public class TableDetails
{

	/**
	 * The primary key column name list.
	 **/
	private List<String> pkColumnNameList;

	/**
	 * The foreign key column name, parent map.
	 **/
	private Map<String, String[]> fkColNamPrntMap;

	/**
	 * The parent table name list.
	 **/
	private List<String> parentTableNameList;

	/**
	 * The constraint name, unique key column name map.
	 **/
	private Map<String, List<String>> constraintNamUKColNamMap;

	/**
	 * The columns list.
	 **/
	private List<Column> columnList;

	/**
	 * Instantiates a new table details.
	 *
	 * @param pkColumnNameList
	 *            the primary key column name list
	 * @param fkColNamPrntMap
	 *            the foreign key column name, parent map
	 * @param parentTableNameList
	 *            the parent table name list
	 * @param constraintNamUKColNamMap
	 *            the constraint name, unique key column name map
	 * @param columnList
	 *            the column list
	 */
	public TableDetails(List<String> pkColumnNameList, Map<String, String[]> fkColNamPrntMap, List<String> parentTableNameList, Map<String, List<String>> constraintNamUKColNamMap, List<Column> columnList)
	{
		this.pkColumnNameList = pkColumnNameList;
		this.fkColNamPrntMap = fkColNamPrntMap;
		this.parentTableNameList = parentTableNameList;
		this.constraintNamUKColNamMap = constraintNamUKColNamMap;
		this.columnList = columnList;
	}

	/**
	 * Gets the primary key column name list.
	 *
	 * @return the primary key column name list
	 */
	public List<String> getPkColumnNameList()
	{
		return pkColumnNameList;
	}

	/**
	 * Sets the primary key column name list.
	 *
	 * @param pkColumnNameList
	 *            the new primary key column name list
	 */
	public void setPkColumnNameList(List<String> pkColumnNameList)
	{
		this.pkColumnNameList = pkColumnNameList;
	}

	/**
	 * Gets the foreign key column name parent map.
	 *
	 * @return the foreign key column name parent map
	 */
	public Map<String, String[]> getFkColNamPrntMap()
	{
		return fkColNamPrntMap;
	}

	/**
	 * Sets the foreign key column name parent map.
	 *
	 * @param fkColNamPrntMap
	 *            the foreign key column name parent map
	 */
	public void setFkColNamPrntMap(Map<String, String[]> fkColNamPrntMap)
	{
		this.fkColNamPrntMap = fkColNamPrntMap;
	}

	/**
	 * Gets the parent table name list.
	 *
	 * @return the parent table name list
	 */
	public List<String> getParentTableNameList()
	{
		return parentTableNameList;
	}

	/**
	 * Sets the parent table name list.
	 *
	 * @param parentTableNameList
	 *            the new parent table name list
	 */
	public void setParentTableNameList(List<String> parentTableNameList)
	{
		this.parentTableNameList = parentTableNameList;
	}

	/**
	 * Gets the constraint name, unique key column name map.
	 *
	 * @return the constraint name, unique key column name map
	 */
	public Map<String, List<String>> getConstraintNamUKColNamMap()
	{
		return constraintNamUKColNamMap;
	}

	/**
	 * Sets the constraint name, unique key column name map.
	 *
	 * @param constraintNamUKColNamMap
	 *            the constraint name, unique key column name map
	 */
	public void setConstraintNamUKColNamMap(Map<String, List<String>> constraintNamUKColNamMap)
	{
		this.constraintNamUKColNamMap = constraintNamUKColNamMap;
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
}
