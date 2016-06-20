package com.yukthi.tools.db.data.migration;

import java.util.Iterator;
import java.util.List;
import com.yukthi.tools.db.model.TableInfo;
import com.yukthi.tools.db.model.TableRow;

/**
 * The Interface IDataSource has methods to get data from source.
 */
public interface IDataSource
{
	/**
	 * Gets the tables in order.
	 *
	 * @return the tables in order
	 */
	public List<TableInfo> getTablesInOrder();

	/**
	 * Gets the records.
	 *
	 * @param tableInfoList
	 *            the table info list
	 * @return the records
	 */
	public Iterator<TableRow> getRecords(List<TableInfo> tableInfoList);

	/**
	 * Close resources.
	 */
	public void closeResources();
}
