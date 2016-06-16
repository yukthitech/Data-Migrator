package com.yukthi.tools.db.connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.yukthi.tools.db.exception.MigrationException;

/**
 * The Class ConnectionUtils. This class creates connection to interact with the
 * database.
 * 
 * @author Pritam
 */
public class ConnectionUtils
{
	/**
	 * The connection.
	 **/
	private Connection connection;

	/**
	 * Receives properties file which has details about database and create
	 * connections then returns it, if already connection is created it will
	 * return the created connection.
	 *
	 * @param properties
	 *            the properties
	 * @return the connection
	 */
	public Connection getConnection(Properties properties)
	{
		if(connection == null)
		{
			String driverName = properties.getProperty("driverName");
			String url = properties.getProperty("url");
			
			if(driverName == null)
			{
				throw new IllegalArgumentException("Please follow the proper format: 'driverName = databaseDriverName' in properties file");
			}
			
			if(url == null)
			{
				throw new IllegalArgumentException("Please follow the proper format: 'url = databaseUrl' in properties file");
			}
			
			try
			{
				Class.forName(driverName);

				connection = (Connection) DriverManager.getConnection(url, properties);
			} catch(Exception ex)
			{
				throw new MigrationException("Error occured while creating connection", ex);
			}
			return connection;
		}
		return connection;
	}

	/**
	 * Close ResultSet Statement.
	 *
	 * @param resultSet
	 *            the result set
	 * @param statement
	 *            the statement
	 */
	public void closeResStat(ResultSet resultSet, Statement statement)
	{
		if(resultSet != null)
		{
			try
			{
				resultSet.close();
			} catch(SQLException e)
			{
				throw new MigrationException("An error occured while closing the Result set", e);
			}
		}
		if(statement != null)
		{
			try
			{
				statement.close();
			} catch(SQLException e)
			{
				throw new MigrationException("An error occured while closing the statement", e);
			}
		}
	}

	/**
	 * Clean up method for closing the connection.
	 */
	public void cleanUp()
	{
		if(connection != null)
		{
			try
			{
				connection.close();
			} catch(SQLException e)
			{
				throw new MigrationException("Error occured while closing the connection", e);
			}
		}
	}
}
