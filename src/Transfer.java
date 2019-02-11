import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Transfer implements Runnable{
	String page;
	HttpServletRequest request;
	HttpServletResponse response;


	public Transfer(String page, HttpServletRequest request, HttpServletResponse response) {
		super();
		this.page = page;
		this.request = request;
		this.response = response;
	}
	
	@Override
	public void run()
	{
		move(request,response,page);
	}


	public static void move(HttpServletRequest request, HttpServletResponse response,String page) {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
