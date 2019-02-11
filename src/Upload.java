

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/Upload")
@MultipartConfig(maxFileSize = 109544252 ) 
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		//System.out.println(name);
		Part part=request.getPart("file");
		int rslt=0;
		if(part!=null) {
			//System.out.println("working here "+part);
			ExecutorService es=Executors.newSingleThreadExecutor();
			InputStream is=part.getInputStream();
			try
			{
			//	System.out.println(part.getSize());
				Callable t1=(Callable) new Helper(is,name,part.getSize());
				Set<Callable<Integer>> c=new HashSet<Callable<Integer>>();
				c.add(t1);
				int f=es.invokeAny(c);
				
				if(f!=0)
					request.setAttribute("done", "File uploaded successfully");
				else
					request.setAttribute("error", "Error in file uploading");
				Runnable t2=new Transfer("page.jsp",request,response);
				es.submit(t2);
				/*Thread t2=new Transfer("page.jsp",request,response);
				t2.start();*/
				//RequestDispatcher rd = request.getRequestDispatcher("page.jsp");
				
				
			}
			catch(Exception e)
			{
				//rslt=0;
				e.printStackTrace();
			}
			finally
			{
				es.shutdown();
			}
		}
		else System.out.println("null");
		/*if(rslt==0)
		{
			System.out.println("Cannot be uploaded");
			request.setAttribute("error", "File cannot be uploaded due to some problem");
		}
		else
		{
			request.setAttribute("done", "Uploaded successfully");
		}*/
		
	}

}
