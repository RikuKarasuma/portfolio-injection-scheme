package xyz.eureka.software;

public final class Globals 
{
	private static final String PORTFOLIO_FILE_PATH = "PATH\\TO\\PORTFOLIO\\XML\\Portfolio.xml";
	private static StringBuffer PORTFOLIO_JSON = new StringBuffer();
	
	public static String getPortfolioFilePath()
	{
		return PORTFOLIO_FILE_PATH;
	}
	
	/**
	 * JSON Array filled with Portfolio Objects. Retrieved via REST GET.
	 * @param new_json - New Portfolio JSON to set.
	 */
	public static void setPortfolioJSON(StringBuffer new_json)
	{
		PORTFOLIO_JSON = new_json;
	}
	
	/**
	 * JSON Array filled with Portfolio Objects. Retrieved via REST GET
	 * within {@link GetPortfolio}.
	 * @return Portfolio JSON Array.
	 */
	public static StringBuffer getPortfolioJSON()
	{
		return PORTFOLIO_JSON;
	}
}
