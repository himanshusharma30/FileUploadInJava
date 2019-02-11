import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.Callable;
public class Helper  implements Callable{
	InputStream in;
	String name;
	long size;
	
	
	public Helper(InputStream in, String name, long size) {
		super();
		this.in = in;
		this.name = name;
		this.size = size;
	}
	
	@Override
	public Integer call()throws Exception
	{
		int x=0;
		try
		{
			x=upload(this.in,this.name,this.size);
			System.out.println("File uploaded successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return x;
	}


	public static int upload(InputStream in,String name,long size)throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://192.168.1.64:3306/test","root","Libsys@1234");
		PreparedStatement ps=con.prepareStatement("insert into test(file,name) values(?,?)");
		ps.setBlob(1, in);
		ps.setBlob(1, in, size);
		ps.setString(2, name);
		int s=ps.executeUpdate();
		//System.out.println(s);
		return s;
		
	}

}
