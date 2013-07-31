


import java.net.*;
import java.io.*;
import java.util.*;



class EchoServer
{
static ServerSocket ssock;
static Socket s1,s2;
static Socket[] sss;
static int i=1;
static jdbc j;
static PrintWriter output11;

	public static void main(String ar[])

	{
	sss=new Socket[100];
  j=new jdbc();
	try
	{

	            ssock=new ServerSocket(4444,10);

				System.out.println("EchoServer is waiting on port "+ssock.getLocalPort());

		        while(true)
		        {
		        Socket sk=ssock.accept();
				    
		        System.out.println("Connect to client  "+sk);
						
		        new ProcessClient(sk).start();
		        }
	}

	catch(Exception ex)
	{
	System.out.println("Exception :"+ex.getMessage());
	System.exit(0);
	}


    }    //End of main


}



class ProcessClient extends Thread
{
Socket sk,sk1,sk2;
BufferedReader input;
PrintWriter output;
String user,passwd,ipadd,portno;
	ProcessClient(Socket  pw)
	{
	sk=pw;
	EchoServer.s1=pw;
	ipadd=String.valueOf(sk.getInetAddress());
	ipadd=ipadd.substring(1);
	portno=String.valueOf(sk.getPort());
	System.out.println(ipadd+"sdfd"+portno);
	System.out.println(sk);
	}


	public void run()
	{
   //int a=0;
			try
			{
				
       // if(EchoServer.i!=2)
				{
				
				
		input= new BufferedReader(new InputStreamReader(sk.getInputStream()));
			output=new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())),true);
EchoServer.output11=output;
			while(true)
			{  
		
			   // if(input.ready())
			    // {
						/////////////////////////////////////////////////
								System.out.println("hi this is pradeep testing3....");
								
						
					System.out.println("hi this line testing");	
			     String line=input.readLine();
				
				 System.out.println(line);
						
						{ 
								System.out.println("hi this is pradeep testing4....");
								
						 if(line.startsWith("$user11"))
						 {
							 int i=0,k=0;
								
								i=line.indexOf("$passwd",0);
								k=7;
								user="";
								while(k<i)
								{
								 user=user.concat(String.valueOf(line.charAt(k)));
									k++;
								}
							
								
								k=i+7;
								i=line.indexOf("$endoff",0);
								passwd="";
								while(k<i)
								{
								 passwd=passwd.concat(String.valueOf(line.charAt(k)));
									k++;
								}
								
								if(EchoServer.j.isVerified(user,passwd))
								{
								 output.println("$correc$endoff");
									EchoServer.j.setOnline(user,passwd,ipadd,portno);
									EchoServer.sss[EchoServer.j.getSock(user)]=sk;
								}
								else
								{
								 output.println("$incorr$endoff");
								}
						 }
							else
							if(line.startsWith("$exit11"))
							{
							 	 int i=0,k=0;
									i=line.indexOf("$endoff",0);
								k=7;
								user="";
								while(k<i)
								{
								 user=user.concat(String.valueOf(line.charAt(k)));
									k++;
								}
								
								EchoServer.j.setOffline(user);
							}
							else
							if(line.startsWith("$refres"))
							{
							 output.println(EchoServer.j.doRefresh());
							}
							else
							if(line.startsWith("$user10"))
							{
							 try{
								System.out.println(line);
								
                PrintWriter output1;
								
								int i=0,k=0;
									i=line.indexOf("$messag",0);
								  k=line.indexOf("$user01",0)+7;
								user="";
								while(k<i)
								{
								 user=user.concat(String.valueOf(line.charAt(k)));
									k++;
								}
								
								
								output1=new PrintWriter(new BufferedWriter(new OutputStreamWriter(EchoServer.sss[EchoServer.j.getSock(user)].getOutputStream())),true);
								output1.println(line);
								}
								catch(Exception ex)
								{System.out.println("Clos789789e client..."+ex.getMessage());
								}
							}
							
						}
			     System.out.println(line);
						
						
				 //output.println(line+line.length());
			    // }
						
			  }

				}

			}
			catch(Exception ex)
			{
			 System.out.println("Closing the client..."+ex.getMessage());
				try
				{
				sk.close();
				}
				catch(Exception e){}

			}



	}  //End of run method


}
