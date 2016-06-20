package com.yukthi.tools.db.data.migration;

import java.util.List;
import com.yukthi.tools.db.model.TableInfo;
import com.yukthi.tools.db.model.TableRow;

/**
 * The Interface IDataStorage has methods to save data.
 */
public interface IDataStorage
{
	/**
	 * Creates the tables.
	 *
	 * @param tableInfoList
	 *            the table info list
	 */
	public void createTables(List<TableInfo> tableInfoList);

	/**
	 * Persist.
	 *
	 * @param tableInfo
	 *            the table info
	 * @param tableRow
	 *            the table row
	 */
	public void persist(TableInfo tableInfo, TableRow tableRow);

	/**
	 * Commit the records and close all the resources.
	 */
	public void commit();
}
