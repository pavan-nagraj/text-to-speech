import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.sql.*;
public class jdbc
{
 Connection c1;
 Statement s1;
	public jdbc()
	{
		try
		{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		String filename = "C:/workspace/Echosrc/database/account1.mdb";
            String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
            database+= filename.trim() + ";DriverID=22;READONLY=true}"; // add on to the end 
            // now we can get the connection from the DriverManager
            c1 = DriverManager.getConnection( database ,"","");
		
		
		s1=c1.createStatement();
		/*
		ResultSet rs1=s1.executeQuery("SELECT * FROM acc");
			 while(rs1.next())
		 {
			System.out.println(rs1.getString(1)+" "+rs1.getString(2)+" "+rs1.getString(3)+" "+rs1.getString(4)+" "+rs1.getString(5));
		 }*/
			s1.execute("UPDATE acc SET online=0");
			s1.execute("UPDATE acc SET passwd='hhhh' WHERE user='geoff'");
		}
		catch(Exception e)
		{
		System.out.println(e.getMessage());
		}
	
	}
	
	public void showDetails()
	{
	 try{
		ResultSet rs1=s1.executeQuery("SELECT * FROM acc");
		 while(rs1.next())
		 {
			System.out.println(rs1.getString(1)+" "+rs1.getString(2)+" "+rs1.getString(3)+" "+rs1.getString(4)+" "+rs1.getString(5));
		 }
			}
			catch(Exception e)
		{
		System.out.println(e.getMessage());
		}
	}
	
	public boolean isUser(String user)
	{
	 try
		{
	  ResultSet rs1=s1.executeQuery("SELECT * FROM acc WHERE user='"+user+"'");
	  if(rs1.next())return(true);
		return(false);
		}
		catch(Exception e)
		{
		System.out.println(e.getMessage());
		return(false);
		}
	}
	
	public String doRefresh()
	{
	 String userS=new String();
		int i=0;
	try
		{
	  ResultSet rs1=s1.executeQuery("SELECT user FROM acc WHERE online=1");
	   while(rs1.next())
		 {
			userS=userS.concat("$user00"+rs1.getString(1));
		  i++;
		 }
		userS=userS.concat("$count1"+i+"$endoff"); 
		return(userS);
		}
		catch(Exception e)
		{
		System.out.println(e.getMessage());
		userS="$endoff"; 
		return(userS);
		}
	}
	
	public boolean isVerified(String user,String passwd)
	{
	 try
		{
	  ResultSet rs1=s1.executeQuery("SELECT * FROM acc WHERE user='"+user+"' AND passwd='"+passwd+"'");
	  if(rs1.next())
		{
		 return(true);
		}
		return(false);
		}
		catch(Exception e)
		{
		System.out.println(e.getMessage());
		return(false);
		}
	}
	
	
	public boolean isOnline(String user)
	{
	 try
		{
	  ResultSet rs1=s1.executeQuery("SELECT * FROM acc WHERE user='"+user+"'");
	  if(rs1.next())
		{
		if(rs1.getString(5).equals("1"))
		return(true);
		else
		return(false);
		}
		return(false);
		}
		catch(Exception e)
		{
		System.out.println(e.getMessage());
		return(false);
		}
	}
	
	
	
	
	public void setOnline(String user,String passwd,String ipadd,String portno)
	{
	 try{
		s1.execute("UPDATE acc SET passwd='"+passwd+"',ipadd='"+ipadd+"',portno='"+portno+"',online=1 WHERE user='"+user+"'");
		  }
			catch(Exception e)
		{
		System.out.println(e.getMessage());
		}
		
	}
	
	
		public void setOffline(String user)
	{
	 try{
		s1.execute("UPDATE acc SET ipadd='0',portno='0',online=0 WHERE user='"+user+"'");
		  }
			catch(Exception e)
		{
		System.out.println(e.getMessage());
		}
		
	}
	
		public String getIP(String user)
	{
	 try{
	  	ResultSet rs1=s1.executeQuery("SELECT ipadd FROM acc WHERE user='"+user+"'");
	    if(rs1.next())
	  	{
		   return(rs1.getString(1));
	  	}
	  	return("0");
		  }
			catch(Exception e)
		{
		System.out.println(e.getMessage());
	  	return("0");
	  }
		
	}
	
		public String getPortNo(String user)
	{
	 try{
	  	ResultSet rs1=s1.executeQuery("SELECT portno FROM acc WHERE user='"+user+"'");
	    if(rs1.next())
	  	{
		   return(rs1.getString(1));
	  	}
	  	return("0");
		  }
			catch(Exception e)
		{
		System.out.println(e.getMessage());
	  	return("0");
	  }
		
	}
	
	public int getSock(String user)
	{
	 try{
	  	ResultSet rs1=s1.executeQuery("SELECT sock FROM acc WHERE user='"+user+"'");
	    if(rs1.next())
	  	{
		   return(Integer.parseInt(rs1.getString(1)));
	  	}
	  	return(0);
		  }
			catch(Exception e)
		{
		System.out.println(e.getMessage());
	  	return(0);
	  }
		
	}
	

}

