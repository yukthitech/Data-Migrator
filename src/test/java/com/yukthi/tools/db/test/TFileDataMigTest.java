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
import com.yukthi.tools.db.file.FileDataSource;
import com.yukthi.tools.db.file.FileDataStorage;
import com.yukthi.tools.db.model.TableInfo;
import com.yukthi.tools.db.model.TableRow;
import com.yukthi.tools.db.rdbms.RdbmsDataSource;

/**
 * The Class TFileDataMigTest. Test class to read data from the file.
 * 
 * @author Pritam
 */
public class TFileDataMigTest
{
	/**
	 * The logger.
	 **/
	private static Logger logger = LogManager.getLogger(TFileDataMigTest.class);

	/**
	 * The Constant SOURCE_FILE_PATH.
	 **/
	private static final String DB_SOURCE_FILE_PATH = "/com.yukthi.properties/testmysql.properties";

	/**
	 * The Constant STORAGE_FILE_PATH.
	 **/
	private static final String STORAGE_FILE_PATH = "/com.yukthi.properties/testfile.properties";

	/**
	 * The Constant QUERIES_FILE_PATH.
	 **/
	private static final String DB_QUERIES_FILE_PATH = "/com.yukthi.properties/mysqlQueries.txt";

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
	 * The properties source.
	 **/
	private Properties propertiesSource = null;

	/**
	 * The table info list.
	 **/
	private List<TableInfo> tableInfoList;

	/**
	 * Creates all the tables and insert records for test.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             the SQL exception
	 */
	@BeforeClass
	public void initTables() throws IOException, SQLException
	{
		logger.trace("Loading the query file");
		InputStream inputStreamQueries = TFileDataMigTest.class.getResourceAsStream(DB_QUERIES_FILE_PATH);
		String queries = IOUtils.toString(inputStreamQueries);

		logger.trace("Loading the properties file");
		InputStream inputStreamSource = TFileDataMigTest.class.getResourceAsStream(DB_SOURCE_FILE_PATH);
		propertiesSource = new Properties();
		propertiesSource.load(inputStreamSource);

		logger.trace("Getting the connection");
		connectionUtils = new ConnectionUtils();
		connection = connectionUtils.getConnection(propertiesSource);
		statement = (Statement) connection.createStatement();

		logger.trace("Creating and inserting the records: " + propertiesSource.get("url"));
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
	 * Read file.Test case to check whether file is containing all the records
	 * or not.
	 *
	 * @param tableNames
	 *            the table names
	 * @param tabRecords
	 *            the tab records
	 * @param propertiesSource
	 *            the properties source
	 */
	@Test(dataProvider = "getProperties")
	public void testReadDataFromFile(List<String> tableNames, List<String> tabRecords, Properties propertiesSource)
	{
		FileDataSource fileDataSource = new FileDataSource(propertiesSource);
		Iterator<?> fileIterator = fileDataSource.getRecords(fileDataSource.getTablesInOrder());

		logger.trace("Reading data from the file");

		while(fileIterator.hasNext())
		{
			TableRow tableRow = (TableRow) fileIterator.next();

			if(tableNames.contains(tableRow.getTableName().toUpperCase()))
			{
				if(!(tabRecords.contains(tableRow.getRowValues()[1])))
				{
					Assert.assertFalse(true, "Table records didnt matched with existing records in file " + tableRow.getTableName());
				}
			}
			else
			{
				Assert.assertFalse(true, "Table details didnt matched with existing tables in file " + tableRow.getTableName());
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
		rdbmsDataSource.getTablesInOrder();

		InputStream inputStreamStorage = TFileDataMigTest.class.getResourceAsStream(STORAGE_FILE_PATH);
		Properties propertiesStorage = new Properties();
		propertiesStorage.load(inputStreamStorage);
		FileDataStorage fileDataStorage = new FileDataStorage(propertiesStorage);

		tableInfoList = rdbmsDataSource.getTablesInOrder();
		fileDataStorage.createTables(tableInfoList);

		Iterator<?> dbmsIterator = rdbmsDataSource.getRecords(tableInfoList);

		logger.trace("Storing data into the file");
		while(dbmsIterator.hasNext())
		{
			fileDataStorage.persist(null, (TableRow) dbmsIterator.next());
		}

		return new Object[][] { { tableNames, tabRecords, propertiesStorage } };
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
