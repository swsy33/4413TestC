package adhoc;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class FinePrint
*/
@WebFilter(
		dispatcherTypes = {DispatcherType.FORWARD }				, 
		urlPatterns =  "/Result.jspx")
 


public class FinePrint implements Filter {

    /**
     * Default constructor. 
     */
    public FinePrint() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		//System.out.println("beforer doFilter");
		HttpServletRequest req = (HttpServletRequest) request;
		String interest = req.getParameter("interest");
		String bank = req.getParameter("bank");
		if(((interest == null) || interest.isEmpty()) && bank.equalsIgnoreCase("CIBC"))
		{
			HttpServletResponse res = (HttpServletResponse) response;
			res = new MyResponse(res);
			
			chain.doFilter(request, res);
			String old = ((MyResponse) res).getContent();
			old = old.replaceAll("the monthly payment is", "the monthly payment is*");
			old = old.replaceAll("</form>", "<hr/>*Limitted time offer.</form>");
			response.getWriter().print(old);
		}
		else
		{
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		//System.out.println("in filter init");
	}

}
