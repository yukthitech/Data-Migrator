package com.yukthi.tools.db.file;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

import com.yukthi.tools.db.data.migration.IDataSource;
import com.yukthi.tools.db.exception.MigrationException;
import com.yukthi.tools.db.model.TableInfo;
import com.yukthi.tools.db.model.TableRow;

/**
 * The Class FileDataSource.This class deals with reading the objects from the
 * file and pass it to data migration manager.
 */
public class FileDataSource implements IDataSource
{
	/**
	 * The Class FileIterator. The inner class to iterate the values from file.
	 */
	private class FileIterator implements Iterator<TableRow>
	{
		/**
		 * The table row.
		 **/
		private TableRow tableRow;

		/**
		 * Returns true if table row is not null or else returns false.
		 */
		public boolean hasNext()
		{
			try
			{
				tableRow = (TableRow) objectInputStream.readObject();

			} catch(EOFException ex)
			{
				return false;
			}catch(Exception e)
			{
				throw new NoSuchElementException("An error occured while reading the file: " + fileName + e);
			}

			if(tableRow != null)
			{
				return true;
			}

			return false;
		}

		/**
		 * Returns the TableRow object which has records of specific table.
		 */
		public TableRow next()
		{
			TableRow res = tableRow;
			tableRow = null;

			return res;
		}
	}

	/**
	 * The table info list.
	 **/
	private List<TableInfo> tableInfoList;

	/**
	 * The file input stream.
	 **/
	private FileInputStream fileInputStream;

	/**
	 * The object input stream.
	 **/
	private ObjectInputStream objectInputStream;

	/**
	 * The file name.
	 **/
	private String fileName;

	/**
	 * Instantiates a new file data source. Creates FileInputStream and
	 * ObjectInputStream for reading object.
	 *
	 * @param properties
	 *            the properties
	 */
	public FileDataSource(Properties properties)
	{
		fileName = properties.getProperty("fileName");

		if(fileName == null)
		{
			throw new IllegalArgumentException("Please follow the proper format: 'fileName = sourceFileName' in properties file");
		}
		
		try
		{
			fileInputStream = new FileInputStream(fileName);
			objectInputStream = new ObjectInputStream(fileInputStream);
		} catch(IOException ex)
		{
			throw new MigrationException("An error occured while reading file: " + fileName, ex);
		}
	}

	/**
	 * Reads the list of TableInfo and returns it.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TableInfo> getTablesInOrder()
	{
		try
		{
			tableInfoList = (List<TableInfo>) objectInputStream.readObject();
		}catch(Exception e)
		{
			throw new MigrationException("An error occured while reading the file: " + fileName, e);
		}
		return tableInfoList;
	}

	/**
	 * Returns the new instance of FileIterator.
	 */
	@Override
	public FileIterator getRecords(List<TableInfo> tableInfoList)
	{
		return new FileIterator();
	}
	
	@Override
	public void closeResources()
	{
		if(objectInputStream != null)
		{
			try
			{
				objectInputStream.close();
			} catch(IOException e)
			{
				throw new MigrationException("Error occured while closing the ObjectInputStream", e);
			}
		}
		
		if(fileInputStream != null)
		{
			try
			{
				fileInputStream.close();
			} catch(IOException e)
			{
				throw new MigrationException("Error occured while closing the FileInputStream", e);
			}
		}
	}
}