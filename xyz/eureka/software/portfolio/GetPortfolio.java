package xyz.eureka.software.portfolio;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.eureka.software.Globals;

/**
 * REST end point that passes Portfolio JSON String to client. Used within AJAX
 * call.
 * 
 * @author Owen McMonagle.
 * @version 0.3
 * 
 * @see Globals
 */
public final class GetPortfolio extends HttpServlet 
{

	private static final long serialVersionUID = -4269222673475500522L;

	/**
	 * Writes Portfolio JSON through {@link HttpServletResponse} stream in UTF-8.
	 * @param req - Client HTTP Request.
	 * @param resp - Client HTTP Response.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		//if(Globals.isRequestValid(req))
		//{
			// Set response as UTF.
			final String charset = "utf-8";
			resp.setContentType("Content-Type: application/json; charset="+charset); 
			resp.setCharacterEncoding("UTF-8");
			OutputStream output = resp.getOutputStream();
			// Write Portfolio JSON to stream.
			output.write(Globals.getPortfolioJSON().toString().getBytes(charset));
			// Flush bytes down stream.
			output.flush();
			// Close stream.
			output.close();
		//}
	}
}
