package xyz.eureka.software.portfolio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Reads in XML from a file. Stores XML within StringBuffer for processing.
 * 
 * @author Owen McMonagle.
 * @version 0.2
 * 
 * @see CreatePortfolioJSON
 */
public abstract class ReadXML 
{
	/**
	 * StringBuffer to store XML.
	 */
	private StringBuffer xml = null;
	
	/**
	 * Taking in the XML file path. Stores XML within StringBuffer.
	 * 
	 * @param file_path - Path to the XML file.
	 */
	public ReadXML(String file_path)
	{
		// Read in XML.
		xml = read(file_path);
	}
	
	/**
	 * Retrieves the XML string stored in a buffer.
	 * @return Read in XML.
	 */
	public StringBuffer getXML()
	{
		return xml;
	}
	
	/**
	 * Reads in a XML line by line. Skipping the first. Returns read in
	 * XML data as a StringBuffer for processing. 
	 * @param file_path - Path to XML file.
	 * @return XML data.
	 */
	private static StringBuffer read(final String file_path)
	{
		// Get file instance.
		final File xml_file = new File(file_path);	
		// Buffer to store XML
		final StringBuffer read = new StringBuffer();
		FileInputStream stream;
		// File Reader.
		BufferedReader reader = null;
		try
		{
			// Open connection to file.
			stream = new FileInputStream(xml_file);
			// Create buffered stream reader.
			reader = new BufferedReader(new InputStreamReader(stream));
			// Skip first line which defines XML char set.
			reader.readLine();
			// String to store new line.
			String new_line = "";
			// While each read in line is not empty...
			while((new_line = reader.readLine()) != null)
				// Store new line in buffer.
				read.append(new_line.trim());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// If XML file exists...
			if(xml_file != null)
				// Close stream reader.
				closeReader(reader);
		}
		
		// Return read in XML data.
		return read;
	}
	
	/**
	 * Closes buffered stream reader. 
	 * 
	 * @param reader - Stream that reads XML file.
	 */
	private static void closeReader(BufferedReader reader)
	{
		try
		{
			if(reader != null)
				reader.close();	
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
