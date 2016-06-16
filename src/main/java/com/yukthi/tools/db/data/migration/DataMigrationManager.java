package com.yukthi.tools.db.data.migration;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yukthi.tools.db.model.TableInfo;
import com.yukthi.tools.db.model.TableRow;

/**
 * The Class DataMigrationManager. This class has one method(migrate) which
 * receives IDataSource and IDataStorage values and interact with respective
 * classes method for storing the data
 * 
 * @author Pritam
 */
public class DataMigrationManager
{
	/**
	 * Migrate method receives the IDataSource and IDataStorage objects and
	 * calls respective methods for saving the data.
	 *
	 * @param dataSource the data source
	 * @param dataStorage the data storage
	 */
	public void migrate(IDataSource dataSource, IDataStorage dataStorage)
	{
		if(dataSource == null)
		{
			throw new NullPointerException("Data source is null");
		}
		
		if(dataStorage == null)
		{
			throw new NullPointerException("Data storage is null");
		}
		
		// get tables in order
		List<TableInfo> tableInfos = dataSource.getTablesInOrder();

		// storing table info list
		dataStorage.createTables(tableInfos);

		// Iterator to iterate the records
		Iterator<TableRow> iterator = dataSource.getRecords(tableInfos);
		
		Map<String, TableInfo> nameToTable = tableInfos.stream()
				.collect(Collectors.<TableInfo, String, TableInfo>toMap(table -> table.getTableName(), table -> table));
		
		TableRow row = null;

		while(iterator.hasNext())
		{
			row = iterator.next();

			// save
			dataStorage.persist(nameToTable.get(row.getTableName()), row);
		}
		
		dataStorage.commit();
		dataSource.closeResources();
	}
}
