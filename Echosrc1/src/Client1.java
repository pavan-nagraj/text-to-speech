import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;



public class Client1
{

static InetAddress ia;
static BufferedReader console;
static BufferedReader buff;
static PrintWriter output;
String strsend,strrep;
Socket sk;
chat1 ch1;


	public Client1()
	{

	
		try
		{

		ia=InetAddress.getByName("newgen-a8545261");
		System.out.println("Connectin to:"+ia);
		
		sk=new Socket(ia,4444);
   
		output=new PrintWriter( new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())),true);
		
		//ProcessOutput();
		

	 
	
		//new ProcessOutput().start();
		new ProcessInput(sk).start();
		//}



		}
		catch(Exception ex)
		{
		System.out.println(ex.getMessage());

		}



	}	//End of main


public void ProcessOutput() 
{

		try
		{
	 // output=new PrintWriter( new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())),true);
		output.println(strsend);
    }
	  catch(Exception ex)
	  {
	  System.out.println(ex.getMessage());
    }
	 	
  
}



}


class ProcessInput extends Thread
{
Socket sk;
BufferedReader buff;
	ProcessInput(Socket  pw)
	{
	 sk=pw;
	}
 
	
	public void run()
	{
		try
		{
	 // buff= new BufferedReader(new InputStreamReader(sk.getInputStream()));
	   while(true)
		{
		 buff= new BufferedReader(new InputStreamReader(sk.getInputStream()));
		login.c1.strrep=buff.readLine();
		System.out.println(login.c1.strrep);
				if(login.c1.strrep.startsWith("$correc"))
				{
			
				JOptionPane.showMessageDialog(null,"Login Successful"); 
				login.l.setVisible(false);
				login.c1.ch1=new chat1(login.l.uname2.getText());
				}
				else
				if(login.c1.strrep.startsWith("$incorr"))
				{
				JOptionPane.showMessageDialog(null,"Invalid username/password"); 
				login.l.uname2.setText("");
				login.l.passwd2.setText("");
				login.l.setVisible(true);
				}
		    else
				if(login.c1.strrep.startsWith("$user00"))
				{ 
				 int count=0,i=0,k=0;
				 String c=new String();
				 String user=new String();	
					
					login.c1.ch1.jp.remove(login.c1.ch1.online);
					login.c1.ch1.cp.remove(login.c1.ch1.jp);
					login.c1.ch1.setVisible(true);
				 	
					i=login.c1.strrep.indexOf("$count1",0);
					k=i+7;
					i=login.c1.strrep.indexOf("$endoff",0);
					c="";
								while(k<i)
								{
								 c=c.concat(String.valueOf(login.c1.strrep.charAt(k)));
									k++;
								}
								
					login.c1.ch1.items=new String[Integer.parseInt(c)];
					i=-1;
					while(count<Integer.parseInt(c))
					{
					
					  i=login.c1.strrep.indexOf("$user00",i+1);
					  k=i+7;
					  i=login.c1.strrep.indexOf("$",k);
					  user="";
								while(k<i)
								{
								 user=user.concat(String.valueOf(login.c1.strrep.charAt(k)));
									k++;
								}
								i--;
								System.out.println(user);
					      login.c1.ch1.items[count]=user;
					 count++;
					}
					login.c1.ch1.online=new JList(login.c1.ch1.items);
					login.c1.ch1.jp=new JScrollPane(login.c1.ch1.online);
					login.c1.ch1.online.addListSelectionListener(login.c1.ch1);
					login.c1.ch1.cp.add(login.c1.ch1.jp);
					login.c1.ch1.jp.setBounds(550,50,100,400);
					login.c1.ch1.setVisible(true);
					//login.c1.ch1.setBounds(100,100,700,600);
					
				//System.out.println("login.c1.strrep");
				}
				else
				if(login.c1.strrep.startsWith("$user10"))
				{
				 int i=0,k=0,j=0;
					String sour=new String();
									i=login.c1.strrep.indexOf("$user01",0);
								  k=login.c1.strrep.indexOf("$user10",0)+7;
							
								while(k<i)
								{
								 sour=sour.concat(String.valueOf(login.c1.strrep.charAt(k)));
									k++;
								}
					login.c1.ch1.from2.setText(sour);			
					
					login.c1.ch1.receive1.setText("");
								i=login.c1.strrep.indexOf("$messag",0)+7;
								j=login.c1.strrep.indexOf("$endoff",0);
								k=login.c1.strrep.indexOf("$",i);
								while(k<j)
								{
								 while(i<k)
									{
									 login.c1.ch1.receive1.append(String.valueOf(login.c1.strrep.charAt(i)));
										i++;
									}
									login.c1.ch1.receive1.append("\n");
									k=login.c1.strrep.indexOf("$",i+1);
									i=i+7;
								}
								  while(i<k)
									{
									 login.c1.ch1.receive1.append(String.valueOf(login.c1.strrep.charAt(i)));
										i++;
									}
									login.c1.ch1.tts.speakTheText(login.c1.ch1.receive1.getText());
								System.out.println(login.c1.ch1.receive1.getText());
								  /*login.c1.ch1.receive1.setVisible(true);
								login.c1.ch1.receive1.setBounds(50,50,300,150);
					*/
					
				}
		}
    }
	 catch(Exception ex)
	 {
	 System.out.println(ex.getMessage());
   }
	 	
  }
}


