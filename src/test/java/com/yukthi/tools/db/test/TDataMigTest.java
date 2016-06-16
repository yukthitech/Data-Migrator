package com.yukthi.tools.db.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.yukthi.tools.db.connection.ConnectionUtils;
import com.yukthi.tools.db.data.migration.DataMigrationManager;
import com.yukthi.tools.db.file.FileDataSource;
import com.yukthi.tools.db.file.FileDataStorage;
import com.yukthi.tools.db.model.TableInfo;
import com.yukthi.tools.db.model.TableRow;
import com.yukthi.tools.db.rdbms.RdbmsDataSource;
import com.yukthi.tools.db.rdbms.RdbmsDataStorage;

/**
 * Test class TDataMigTest for migrating data from one database to another
 * database through file.
 * 
 * 
 * @author Pritam
 */
public class TDataMigTest
{
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TDataMigTest.class);

	/**
	 * The Constant SOURCE_FILE_PATH.
	 **/
	private static final String DB_SOURCE_FILE_PATH = "/com.yukthi.properties/dbTestSource.properties";

	/**
	 * The Constant DB_STORAGE_FILE_PATH.
	 **/
	private static final String DB_STORAGE_FILE_PATH = "/com.yukthi.properties/dbTestStorage.properties";

	/**
	 * The Constant QUERIES_FILE_PATH.
	 **/
	private static final String DB_QUERIES_FILE_PATH = "/com.yukthi.properties/mysqlQueries.txt";

	/**
	 * The Constant STORAGE_FILE_PATH.
	 **/
	private static final String STORAGE_FILE_PATH = "/com.yukthi.properties/testfile.properties";

	/**
	 * The properties source.
	 **/
	private Properties propertiesSource = null;

	/**
	 * The connection utils.
	 **/
	private ConnectionUtils connectionUtils = null;

	/**
	 * The connection.
	 **/
	private Connection connection;

	/**
	 * The statement.
	 **/
	private Statement statement = null;

	/**
	 * The table info list.
	 **/
	private List<TableInfo> tableInfoList;

	/**
	 * Creates all the tables and insert records for test.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	@BeforeClass
	public void initTables() throws IOException, ClassNotFoundException, SQLException
	{
		logger.trace("Loading the query file");
		InputStream inputStreamQueries = TDataMigTest.class.getResourceAsStream(DB_QUERIES_FILE_PATH);
		String queries = IOUtils.toString(inputStreamQueries);

		logger.trace("Loading the properties file");
		InputStream inputStreamSource = TDataMigTest.class.getResourceAsStream(DB_SOURCE_FILE_PATH);
		propertiesSource = new Properties();
		propertiesSource.load(inputStreamSource);

		logger.trace("Getting the connection");
		connectionUtils = new ConnectionUtils();
		connection = connectionUtils.getConnection(propertiesSource);
		statement = (Statement) connection.createStatement();

		logger.trace("Creating and inserting the records in : " + propertiesSource.get("url"));
		String queryArr[] = queries.split("\\;");

		for(String query : queryArr)
		{
			query = query.trim();

			if(query.startsWith("CREATE"))
			{
				logger.trace(query);

				statement.execute(query);
			}

			if(query.startsWith("INSERT"))
			{
				logger.trace(query);

				statement.executeUpdate(query);
			}
		}

	}

	/**
	 * Test data mig.
	 *
	 * @param propertiesSource
	 *            the properties source
	 * @param propertiesStorage
	 *            the properties storage
	 * @throws IOException
	 */
	@Test(dataProvider = "getProperties")
	public void testDataMig(Properties propertiesSource, List<String> tableNames, List<String> tabRecords) throws IOException
	{
		FileDataSource fileDataSource = new FileDataSource(propertiesSource);
		InputStream inputStreamStorage = TDataMigTest.class.getResourceAsStream(DB_STORAGE_FILE_PATH);
		Properties propertiesStorage = new Properties();
		propertiesStorage.load(inputStreamStorage);
		RdbmsDataStorage rdbmsDataStorage = new RdbmsDataStorage(propertiesStorage);

		logger.trace("Migrating records from file: " + propertiesSource.get("fileName") + " to database: " + propertiesStorage.get("url"));
		new DataMigrationManager().migrate(fileDataSource, rdbmsDataStorage);

		logger.trace("Reading reocrds from database: " + propertiesStorage.get("url"));
		RdbmsDataSource rdbmsDataSource = new RdbmsDataSource(propertiesStorage);
		Iterator<TableRow> iterator = rdbmsDataSource.getRecords(rdbmsDataSource.getTablesInOrder());

		while(iterator.hasNext())
		{
			TableRow tableRow = iterator.next();

			if(tableNames.contains(tableRow.getTableName().toUpperCase()))
			{
				if(!(tabRecords.contains(tableRow.getRowValues()[1])))
				{
					Assert.assertFalse(true, "Table records didnt matched with new records in another database for " + tableRow.getTableName());
				}
			}
			else
			{
				Assert.assertFalse(true, "Table details didnt matched with new tables in another database for " + tableRow.getTableName());
			}
		}
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@DataProvider
	public Object[][] getProperties() throws IOException
	{
		List<String> tableNames = new ArrayList<String>();
		List<String> tabRecords = new ArrayList<String>();

		// values for
		tableNames.add("DIRECTOR");
		tableNames.add("MANAGER");
		tableNames.add("EMPLOYEE");
		tableNames.add("MEMBERS");

		tabRecords.add("director1");
		tabRecords.add("manager1");
		tabRecords.add("manager2");
		tabRecords.add("employee1");
		tabRecords.add("employee2");
		tabRecords.add("employee3");
		tabRecords.add("employee4");
		tabRecords.add("member1");
		tabRecords.add("member2");
		tabRecords.add("member3");
		tabRecords.add("member4");
		tabRecords.add("member5");
		tabRecords.add("member6");

		RdbmsDataSource rdbmsDataSource = new RdbmsDataSource(propertiesSource);
		tableInfoList = rdbmsDataSource.getTablesInOrder();
		InputStream inputStreamStorage = TDataMigTest.class.getResourceAsStream(STORAGE_FILE_PATH);
		Properties propertiesStorage = new Properties();
		propertiesStorage.load(inputStreamStorage);
		FileDataStorage fileDataStorage = new FileDataStorage(propertiesStorage);
		logger.trace("Migrating records from database: " + propertiesSource.get("url") + " to file: " + propertiesStorage.get("fileName"));

		new DataMigrationManager().migrate(rdbmsDataSource, fileDataStorage);

		return new Object[][] { { propertiesStorage, tableNames, tabRecords } };
	}

	/**
	 * Drop tables.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	@AfterClass
	public void dropTables() throws SQLException
	{
		logger.trace("Droping all the tables: " + propertiesSource.get("url"));

		for(int i = tableInfoList.size() - 1; i >= 0; i--)
		{
			statement.execute("DROP TABLE " + tableInfoList.get(i).getTableName().toUpperCase());
		}

		logger.trace("Closing the connection");
		connectionUtils.closeResStat(null, statement);
		connectionUtils.cleanUp();
	}
}
