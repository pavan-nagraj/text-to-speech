import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class signup extends JFrame implements ActionListener {
	JLabel uname1,passwd1;
	JTextField uname2;
	JPasswordField passwd2; 
	JButton enter,signup;
	
	//static signup l;
	Container cp;
	 Connection c1;
	 Statement s1;
		public signup()
		{
		uname1=new JLabel("User");
		passwd1=new JLabel("Password");
		uname2=new JTextField();
		passwd2=new JPasswordField();
		enter=new JButton("ok");
		
		//client has been comented
		
		
		cp=new Container();
		
		cp=getContentPane();
		setBounds(200,200,400,400);
		cp.add(uname1);
		cp.add(uname2);
		cp.add(passwd1);
		cp.add(passwd2);
		
		cp.add(enter);
		
		
		uname1.setBounds(50,50,100,50);
		uname2.setBounds(200,50,180,50);
		passwd1.setBounds(50,100,100,50);
		passwd2.setBounds(200,100,180,50);
		

		enter.setBounds(200,200,100,50);
		
		enter.addActionListener(this);
		uname2.addActionListener(this);
		passwd2.addActionListener(this);
		
		cp.setLayout(null);
	  
		}
		//public static void main(String args[])
		//{
	    //l=new signup();
		
		// l.setVisible(true);
			
		//	l.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
		//}
		
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==enter)
			{
				String struname2 = (String) uname2.getText();
				String strpasswd2 = (String) passwd2.getText();
				System.out.println(struname2);
				System.out.println(strpasswd2);
		  setVisible(false);
			System.out.println("registered");
			try
			{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String filename = "C:/workspace/Echosrc/database/account1.mdb";
	            String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
	            database+= filename.trim() + ";DriverID=22;READONLY=true}"; // add on to the end 
	            // now we can get the connection from the DriverManager
	            c1 = DriverManager.getConnection( database ,"","");
			
			
			s1=c1.createStatement();
			s1.execute("insert into acc (user,passwd)values('"+struname2+"','"+strpasswd2+"')");
			JOptionPane.showMessageDialog(null,"New User Added Sucessfully!!"); 
login.l.setVisible(true);
//System.exit(0);
				//s1.execute("UPDATE acc SET online=0");
				//s1.execute("UPDATE acc SET passwd='hhhh' WHERE user='geoff'");
			}
			catch(Exception e)
			{
			System.out.println(e.getMessage());
			}
			}
			
		}
}
