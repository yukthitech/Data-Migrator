package com.yukthi.tools.db.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Properties;

import com.yukthi.tools.db.data.migration.IDataStorage;
import com.yukthi.tools.db.exception.MigrationException;
import com.yukthi.tools.db.model.TableInfo;
import com.yukthi.tools.db.model.TableRow;

/**
 * The Class FileDataStorage.This class receives data from the data migration
 * manager and store it in the file.
 * 
 * @author Pritam
 */
public class FileDataStorage implements IDataStorage
{
	/**
	 * The file output stream.
	 **/
	private FileOutputStream fileOutputStream;

	/**
	 * The object output stream.
	 **/
	private ObjectOutputStream objectOutputStream;

	/** 
	 * The file name. 
	 **/
	private String fileName;
	
	/**
	 * Instantiates a new file data storage.Creates FileOutputStream and
	 * ObjectOutputStream for storing the data in the file.
	 *
	 * @param properties
	 *            the properties
	 */
	public FileDataStorage(Properties properties)
	{
		fileName = properties.getProperty("fileName");

		if(fileName == null)
		{
			throw new IllegalArgumentException("Please follow the proper format: 'fileName = yourFileName' in properties file");
		}
		
		try
		{
			fileOutputStream = new FileOutputStream(fileName);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
		} catch(IOException ex)
		{
			throw new MigrationException("An error occured while creating file", ex);
		}
	}

	/**
	 * Writes the list of table info in the file.
	 */
	@Override
	public void createTables(List<TableInfo> tableInfoList)
	{
		try
		{
			if(tableInfoList.isEmpty())
			{
				throw new IllegalArgumentException("There are no tables present in the databse");
			}

			objectOutputStream.writeObject(tableInfoList);
		} catch(IOException ex)
		{
			throw new MigrationException("An error occured while saving the table details in the file", ex);
		}
	}

	/**
	 * Writes the tableRow into the file
	 */
	@Override
	public void persist(TableInfo tableInfo, TableRow tableRow)
	{
		try
		{
			objectOutputStream.writeObject(tableRow);
		} catch(IOException ex)
		{
			throw new MigrationException("An error occured while saving records from table: " + tableRow.getTableName() + " in the file", ex);
		}
	}

	@Override
	public void commit()
	{
		if(objectOutputStream != null)
		{
			try
			{
				objectOutputStream.close();
			} catch(IOException e)
			{
				throw new MigrationException("Error occured while closing the objectOutputStream ", e);
			}
		}
		
		if(fileOutputStream != null)
		{
			try
			{
				fileOutputStream.close();
			} catch(IOException e)
			{
				throw new MigrationException("Error occured while closing the fileOutputStream ", e);
			}
		}
	}
}
