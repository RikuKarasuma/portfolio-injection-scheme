package xyz.eureka.software.portfolio;

import java.util.ArrayList;
import java.util.List;

import xyz.eureka.software.Globals;

/**
 * Converts Portfolio data defined in XML to JSON. Inherits from {@link ReadXML} 
 * in order to read the XML file. 
 * 
 * 
 * @author Owen McMonagle.
 * @version 0.4
 * 
 * @see ReadXML
 * @see Globals
 */
public final class CreatePortfolioJSON extends ReadXML
{
	
	/**
	 * Parses XML. Converts Project XML to JSON, then sets the JSON within
	 * {@link Globals} for use on the REST end point at {@link GetPortfolio}. 
	 */
	public CreatePortfolioJSON() 
	{
		super("C:/Users/Garak/portfolio.xml");//Globals.getVisitorFilePath()(););
		// Define JSON start String. We use this to built the 
		// JSON object Array.
		final String json_start  = "{\"projects\": [";
		// Retrieve each Project XML. 
		List<String> project = getProjects();
		// Print out number of projects.
		System.out.println("Projects #"+project.size());
		// Add JSON start to the JSON StringBuffer.
		StringBuffer json_object_data = new StringBuffer();
		json_object_data.append(json_start);
		// Iterate to create and add each new JSON Project Object...
		for(int i = 0; i < project.size(); i++)
			try
			{
				// Get Project XML.
				String project_data = project.get(i);
				// If XML is not empty...
				if(!project_data.isEmpty())
				{
					// Add new JSON Project String.
					json_object_data.append(
						// Create JSON Object using passed parsed XML data.
						createJSONObject
						(
							// Parse XML Name.
							getName(project_data),
							// Parse XML Header
							getHeader(project_data),
							// Parse XML Body
							getBody(project_data),
							// Parse XML Tags
							getTags(project_data)
						));
					// If we are not at the end of the Project Array...
					if(i != (project.size()-1))
						// Add Comma after each added object.
						json_object_data.append(",");
				}
			}
			catch(Exception e)
			{
				System.out.println(project.get(i));
				e.printStackTrace();
			}
		// Add ending JSON to String.
		json_object_data.append("]}"); 
		// Set the new Portfolio Project JSON.
		Globals.setPortfolioJSON(json_object_data);
	}
	
	/**
	 * Retrieves the XML read in by {@link ReadXML}. Then parses it by splitting
	 * each project with the "</project>" delimiter. Stores each Project inside
	 * an ArrayList for returning.
	 * 
	 * @return ArrayList filled with Projects in XML.
	 */
	private ArrayList<String> getProjects()
	{
		// XML Project end tag.
		final String project_end = "</project>";
		// Get the read in XML data.
		final String xml_data = this.getXML().toString();
		// Split each Project using the "</project>" delimiter.
		String[] split_data = xml_data.split(project_end);
		// List to store each Project
		ArrayList<String> parsed_projects = new ArrayList<>();
		// Iterate over split Project array...
		for(String data: split_data)
			// Add each split project to the ArrayList.
			parsed_projects.add(data);
		
		// Return projects.
		return parsed_projects;
	}
	
	/**
	 * Extracts the name from the Project string.
	 * @param project - XML data to extract from.
	 * @return Extracted Project name.
	 */
	private static String getName(String project)
	{
		final String project_start = "<name>",
				 project_end = "</name>";
		return extract(project_start, project_end, project);
	}
	
	/**
	 * Extracts the header HTML from the Project string.
	 * @param project - XML data to extract from.
	 * @return Extracted Project header HTML.
	 */
	private static String getHeader(String project)
	{
		final String project_start = "<header>",
				 project_end = "</header>";	
		return extract(project_start, project_end, project);
	}
	
	/**
	 * Extracts the body HTML from the Project string.
	 * @param project - XML data to extract from.
	 * @return Extracted Project body HTML.
	 */
	private static String getBody(String project)
	{
		final String project_start = "<body>",
				 project_end = "</body>";	
		return extract(project_start, project_end, project);
	}
	
	/**
	 * Extracts the tags from the Project string.
	 * @param project - XML data to extract from.
	 * @return Extracted Project tags.
	 */
	private static String getTags(String project)
	{
		final String project_start = "<tags>",
				 project_end = "</tags>";	
		return extract(project_start, project_end, project);
	}
	
	/**
	 * Using a start and end Mark up tag. This function extracts a String 
	 * from within those two tags from with the Project String.
	 * 
	 * @param start_tag - Start Mark up tag.
	 * @param end_tag - End Mark up tag.
	 * @param project - XML Project String. 
	 * @return Extracted data between the two past tags.
	 */
	private static String extract(String start_tag, String end_tag, String project)
	{
		int start_index = project.indexOf(start_tag), end_index = project.indexOf(end_tag);
		String data = project.substring(start_index + start_tag.length(), end_index).trim();
		return verifyData(data);
	}
	
	/**
	 * Creates a Portfolio JSON object with the passed parameters. 
	 * 
	 * @param name - Name of the Project.
	 * @param header - Header HTML of the Project.
	 * @param body - Body HTML of the Project.
	 * @param tags - Sorting Tags of the Project.
	 * @return Portfolio JSON Object String.
	 */
	private static String createJSONObject(String name, String header, String body, String tags)
	{
		return ("{\"name\": \""+name+"\", \"header\": \""+header+"\", \"body\": \""+body+"\",\"tags\": [" + splitTags(tags) + "]}");
	}
	
	/**
	 * Splits up the Portfolio Project Tags then recreates them into a JSON compatible string.
	 * @param tags - Project Tags.
	 * @return Rebuilt JSON tags.
	 */
	private static String splitTags(String tags)
	{
		// End of Tag offset. 
		final byte offset = 1;
		// Split the Tags String using the comma delimiter.
		final String[] split_tags = tags.split(",");
		// String to rebuilt JSON Tags.
		String rebuilt_string = "";
		// Iterate over Tags Array...
		for(int i = 0; i < split_tags.length; i++)
			// Rebuild Tags into JSON String.
			rebuilt_string += "\""+split_tags[i].trim()+"\"" +
			// If we are not at the end of the array...
			// add comma.
			( (i != (split_tags.length-offset) ) ? "," : "");
		
		// Return built JSON string.
		return rebuilt_string;
	}
	
	/**
	 * Replaces unsafe symbols to alternatives or safe entity types. Then returns
	 * the safe string.
	 * 
	 * @param data - String to make JSON safe. 
	 * @return Safe JSON String.
	 */
	private static String verifyData(String data)
	{
		return data.replaceAll("\"", "'")
		.replaceAll("/>", " />")
		.replaceAll("\\\\", "&#92;")
		.replaceAll("\n", "")
		.replaceAll("\r", "")
		.replaceAll("\t", "");
	}
}
