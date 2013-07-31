import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

//import javax.swing.event.*;

public class login extends JFrame implements ActionListener
{
JLabel uname1,passwd1;
JTextField uname2;
JPasswordField passwd2; 
JButton enter,signup;
static Client1 c1;
static login l;
static signup su;
Container cp;
	public login()
	{
	uname1=new JLabel("User");
	passwd1=new JLabel("Password");
	uname2=new JTextField();
	passwd2=new JPasswordField();
	enter=new JButton("login");
	signup=new JButton("Sign up");
	//client has been comented
	
	c1=new Client1();
	cp=new Container();
	
	cp=getContentPane();
	setBounds(200,200,400,400);
	cp.add(uname1);
	cp.add(uname2);
	cp.add(passwd1);
	cp.add(passwd2);
	cp.add(signup);
	cp.add(enter);
	
	
	uname1.setBounds(50,50,100,50);
	uname2.setBounds(200,50,180,50);
	passwd1.setBounds(50,100,100,50);
	passwd2.setBounds(200,100,180,50);
	
	signup.setBounds(50,200,100,50);
	enter.setBounds(200,200,100,50);
	signup.addActionListener(this);
	enter.addActionListener(this);
	uname2.addActionListener(this);
	passwd2.addActionListener(this);
	
	cp.setLayout(null);
  
	}
	public static void main(String args[])
	{
    l=new login();
	
	 l.setVisible(true);
		
		l.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource()==enter)
		{
	  l.setVisible(false);
		System.out.println("login");
		c1.strsend="";
		c1.strsend=c1.strsend.concat("$user11"+uname2.getText()+"$passwd"+passwd2.getText()+"$endoff");
	
		c1.ProcessOutput();
		}
		if(event.getSource()==signup)
		{
			l.setVisible(false);
		    su=new signup();
			
			 su.setVisible(true);
				su.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});

		}
	}


}

