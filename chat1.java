
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;

public class chat1 extends JFrame implements ActionListener,ListSelectionListener
{
 JTextArea receive1,send1;
 JButton send,logout,set,play,nxt,prv,refresh;
 JLabel from1,to1,from2,to2;
 JList online;
 String[] items;	
// JPanel pane;
 JScrollPane jp;
	String user1;
	TTS tts;
 Container cp;
	String reader=null;
 public chat1(String user)
	{
	 super("Welcome "+user+" to special chat");
		user1=new String();
		receive1=new JTextArea();
		send1=new JTextArea();
		send=new JButton("SEND");
		logout=new JButton("SignOut");
		set=new JButton("V-Pad");
		play=new JButton("Play");
		nxt=new JButton(">>");
		prv=new JButton("<<");
		refresh=new JButton("REFRESH");
		from1=new JLabel("FROM:");
		to1=new JLabel("TO:");
    from2=new JLabel(user);
		to2=new JLabel(user);
		items=new String[200];
		tts=new TTS();
		
		cp=new Container();
    user1=user;
	
		cp=getContentPane();	
	 /*
	  for(int i=0;i<200;i++)
		{
		 items[i]="itemm"+i;
		}*/
		online=new JList(items);
	jp=new JScrollPane(online);
		
		
		cp.setLayout(null);
		
		
		online.addListSelectionListener(this);
   
		
		
    cp.add(receive1);
		cp.add(send1);
		cp.add(send);	 
    cp.add(logout);
		cp.add(set);
		cp.add(play);	 
   // cp.add(nxt);
		//cp.add(prv);
		cp.add(refresh);	 
    cp.add(from1);
		cp.add(to1);
		cp.add(from2);	 
    cp.add(to2);
		cp.add(jp);
		
	receive1.setBounds(50,50,300,150);
	send1.setBounds(50,300,300,150);
	send.setBounds(250,455,100,25);
	logout.setBounds(550,455,100,25);
	set.setBounds(50,205,90,25);
	play.setBounds(145,205,60,25);
	//nxt.setBounds(210,205,50,25);
	//prv.setBounds(265,205,50,25);
	refresh.setBounds(550,20,100,25);
	from1.setBounds(50,25,50,25);
	to1.setBounds(50,275,50,25);
	from2.setBounds(100,25,100,25);
	to2.setBounds(100,275,100,25);
	
	jp.setBounds(550,50,100,400);

		send1.setLineWrap(true);
		receive1.setLineWrap(true);
		
		
		send.addActionListener(this);
		logout.addActionListener(this);
		set.addActionListener(this);
		play.addActionListener(this);
		nxt.addActionListener(this);
		prv.addActionListener(this);
		refresh.addActionListener(this);
		
		login.c1.strsend="";
		login.c1.strsend=login.c1.strsend.concat("$refres"+"$endoff");
	  login.c1.ProcessOutput();
		
		
		 setVisible(true);
	  setBounds(100,100,700,600);
		
		
		
		addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e)
		{
		login.c1.strsend="";
		login.c1.strsend=login.c1.strsend.concat("$exit11"+login.l.uname2.getText()+"$endoff");
	  login.c1.ProcessOutput();
		System.exit(0);
		}});

	}
	
	public void actionPerformed(ActionEvent event)
	
	{
		if(event.getSource()==set)
		{
			VoicePad voicePad = new VoicePad();
			voicePad.setVisible(true);
		}
	
		if(event.getSource()==play)
		{
		System.out.println("hi this is pradeep");
		}	
		if(event.getSource()==logout)
		{
		login.c1.strsend="";
		login.c1.strsend=login.c1.strsend.concat("$exit11"+login.l.uname2.getText()+"$endoff");
	  login.c1.ProcessOutput();
		System.exit(0);
		}
		if(event.getSource()==refresh)
		{
		login.c1.strsend="";
		login.c1.strsend=login.c1.strsend.concat("$refres"+"$endoff");
	  login.c1.ProcessOutput();
		}
		if(event.getSource()==send)
		{
		String mess=new String();
		String mess2=new String();
		char ch[]=new char[100];
		int i=0,k=0,j=0;
		mess=send1.getText();
		j=mess.indexOf("$endoff",0);
		/*
		while(k<j)
					{
					 ch=new char[100];
					  //i=mess.indexOf("$messag",i+1);
					  //k=i+7;
						
					  i=mess.indexOf("\n",k);
						if(k==i)
						{
						mess2=mess2.concat("$newlin");
						k=i+1;
						}
						else
						{
						getChars(k,i-1,ch,0);
						mess2=mess2.concat(String.valueOf(ch)+"$newlin");
						k=i+1;
						}		
								System.out.println(mes2);
					      
					}
		
		*/
		while(i<mess.length())
		{
		 if(mess.charAt(i)=='\n')
			{
			mess2=mess2.concat("$newlin");
			}
			else
			{ 
			mess2=mess2.concat(String.valueOf(mess.charAt(i)));
			}
		 i++;
		}
		login.c1.strsend="";
		login.c1.strsend=login.c1.strsend.concat("$user10"+user1+"$user01"+to2.getText()+"$messag"+mess2+"$endoff");
	  login.c1.ProcessOutput();
		}
		
	}
  
	
	public void valueChanged(ListSelectionEvent e)
	{
	 to2.setText(String.valueOf(online.getSelectedValue()));
	}

}

