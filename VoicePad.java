

/** 
 * Speech-enabled text editor.  
 * <p>
 * This class demonstrates speech capabilities integrated with a simple text editor.
 * <p>
 * @author <b>Mandar S. Chitnis</b>, Copyright &#169; 2003
 * @author <b>Lakshmi Ananthamurthy</b>, Copyright &#169; 2003
 * @version 1.0, 2003/06/30
 */

import java.io.*;
import java.util.Locale;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import javax.speech.*;
import javax.speech.synthesis.*;

import com.sun.speech.engine.synthesis.*;
import com.sun.speech.freetts.jsapi.FreeTTSEngineCentral;
import java.util.Locale;


import java.io.File;
import java.io.PrintStream;

import javax.speech.Central;
import javax.speech.EngineList;
import javax.speech.synthesis.*;

public class VoicePad extends JFrame
{
	private JScrollPane ps = null;
	private JTextArea textArea = null;
	private JFileChooser fileChooser = null;
	
	private JMenuBar menuBar = null;
	
	private JMenu fileMenu = null; 
	private JMenu speechMenu = null;
	
	private JMenuItem newMenuItem = null;
	private JMenuItem openMenuItem = null; 
	private JMenuItem saveMenuItem = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem playMenuItem = null;
	private JMenuItem pauseMenuItem = null;
	private JMenuItem resumeMenuItem = null;
	private JMenuItem stopMenuItem = null;
	
	private ActionListener myActionListener = null;
	
	// TBD: add necessary variables for speech synthesis
	private Synthesizer synthesizer = null;
	private Voice voice = null;
	private String voiceName = "";
	private String VOICE_SELECTED = "kevin16";
	
	// constructor
	public VoicePad()
	{
		super("Java VoicePad");
		setSize(800, 600);
		
		// initialize the application settings
		init();

		// set up the file selection
		fileChooser = new JFileChooser(); 
		fileChooser.setCurrentDirectory(new File("."));

		WindowListener wndCloser = new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) 
			{
				closeSpeechSynthesisEngine();
				System.exit(0);
			}
		};
		addWindowListener(wndCloser);
	}
	
	// execute voicepad application
	public static void main(String argv[]) 
	{
		VoicePad voicePad = new VoicePad();
		voicePad.setVisible(true);
	}
	
	// initialization
	private void init()
	{
		textArea = new JTextArea();
		ps = new JScrollPane(textArea);
		this.getContentPane().add(ps, BorderLayout.CENTER);

		textArea.append("Voice-enabled editor.");

		initSpeechSynthesisEngine();

		this.setJMenuBar(getVoicePadMenuBar());

		System.out.println("Java voicepad : VoicePad application initialized."); 
	}
	public static void listAllVoices(String modeName)
	{
	System.out.println();
	System.out.println("All " +modeName+ " Mode JSAPI Synthesizers and Voices:");
	SynthesizerModeDesc required = new SynthesizerModeDesc(null, modeName, Locale.US, null, null);
	EngineList engineList = Central.availableSynthesizers(required);
	for(int i = 0; i < engineList.size(); i++)
	{
	SynthesizerModeDesc desc = (SynthesizerModeDesc)engineList.get(i);
	System.out.println(" " +desc.getEngineName()+ " (mode=" +desc.getModeName()+ ", locale=" +desc.getLocale() +"):");
	Voice voices[] = desc.getVoices();
	for(int j = 0; j < voices.length; j++)
	System.out.println(" "+ voices[j].getName());

	}

	}

	private void initSpeechSynthesisEngine()
	{
		/*String message = "";
		String synthesizerName = System.getProperty("synthesizerName","Unlimited domain FreeTTS Speech Synthesizer from Sun Labs");

		// Create a new SynthesizerModeDesc that will match the FreeTTS synthesizer.
		SynthesizerModeDesc desc = new SynthesizerModeDesc(synthesizerName,null,Locale.US,Boolean.FALSE,null);
		// obtain the Speech Synthesizer instance	*/
		listAllVoices("general");
		String voiceName = /*args.length <= 0 ? */"kevin16" /*: args[0]*/;
		System.out.println();
		System.out.println("Using voice: " +voiceName);
		
		SynthesizerModeDesc desc = new SynthesizerModeDesc(null, "general", Locale.US, null, null);
		
		
		try
		{
			synthesizer = Central.createSynthesizer(desc);
			
			if(synthesizer == null)
			{
				 String message = "No synthesizer created. This may be the result of any\nnumber of problems. It's typically due to a missing\n\"speech.properties\" file that should be at either of\nthese locations: \n\n";
				message = message +"user.home : " +System.getProperty("user.home") +"\n";
				message = message +"java.home/lib: " +System.getProperty("java.home")+ File.separator +"lib\n\n"+ "Another cause of this problem might be corrupt or missing\n" +"voice jar files in the freetts lib directory. This problem\n" +"also sometimes arises when the freetts.jar file is corrupt\n" +"or missing. Sorry about that. Please check for these\n" +"various conditions and then try again.\n";
				

				System.out.println("Java voicepad : ERROR! Synthesizer not found!"); 
				System.out.println(message); 

				throw new Exception("Synthesizer not found!");
			}

			System.out.println("Java voicepad : Speech synthesizer obtained."); 

			voiceName = System.getProperty("voiceName", VOICE_SELECTED);
			voice = new Voice(voiceName, Voice.GENDER_DONT_CARE,Voice.AGE_DONT_CARE, null);

			if(voice == null)
			{
				System.out.println("Java voicepad : ERROR! No voice selected!"); 
				throw new Exception("No voice selected!");
			}
			
			System.out.println("Java voicepad : Voice " + VOICE_SELECTED + " selected."); 
			synthesizer.allocate();
			synthesizer.resume();
			synthesizer.getSynthesizerProperties().setVoice(voice);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Java voicepad : ERROR! initializing speech engine." + e); 
			closeSpeechSynthesisEngine();
		}

		System.out.println("Java voicepad : Speech engine initialized."); 
	}
	
	private void closeSpeechSynthesisEngine()
	{
		try
		{
			if(synthesizer != null)
				synthesizer.deallocate();
			System.out.println("Java voicepad : Speech engine shutdown."); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Java voicepad : ERROR! closing speech synthesis engine." + e); 
			closeSpeechSynthesisEngine();
		}
	}
	
	// menu bar creation
	private JMenuBar getVoicePadMenuBar()
	{
		if(menuBar == null)
		{
			menuBar = new JMenuBar();

			menuBar.add(getFileMenu());
			menuBar.add(getSpeechMenu());

			System.out.println("Java voicepad : Menubar added."); 
		}
		
		return menuBar;
	}

	// file menu and file menu items begin	
	private JMenu getFileMenu()
	{
		if(fileMenu == null)
		{
			// create menu
			fileMenu = new JMenu("File");
			myActionListener = 
				new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						;

						System.out.println("Java voicepad : File menu action performed."); 
					}
				};
			fileMenu.setMnemonic('f');
			fileMenu.addActionListener(myActionListener);

			fileMenu.add(getNewMenuItem());
			fileMenu.add(getOpenMenuItem());
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getExitMenuItem());
		
			System.out.println("Java voicepad : File menu created."); 
		}

		return fileMenu;
	}
	
	private JMenuItem getNewMenuItem()
	{
		if(newMenuItem == null)
		{
			newMenuItem = new JMenuItem("New");
			myActionListener = 
				new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						textArea.setText("");
						
						System.out.println("Java voicepad : New menu item action performed."); 
					}
				};
			newMenuItem.setMnemonic('n');
			newMenuItem.addActionListener(myActionListener);

			System.out.println("Java voicepad : New menu item created."); 
		}

		return newMenuItem;
	}

	private JMenuItem getOpenMenuItem()
	{
		if(openMenuItem == null)
		{
			openMenuItem = new JMenuItem("Open");
			myActionListener = 
				new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						VoicePad.this.repaint();
						if(fileChooser.showOpenDialog(VoicePad.this) == JFileChooser.APPROVE_OPTION)
						{
							File fSelected = fileChooser.getSelectedFile();
							try
							{
								FileReader in = new FileReader(fSelected);
								textArea.read(in, null);
								in.close();
							} 
							catch(IOException ioe) 
							{
								ioe.printStackTrace();
							}
						}
						
						System.out.println("Java voicepad : Open menu item action performed."); 
					}
				};
			openMenuItem.setMnemonic('o');
			openMenuItem.addActionListener(myActionListener);
		
			System.out.println("Java voicepad : Open menu item created."); 
		}
		
		return openMenuItem;
	}

	private JMenuItem getSaveMenuItem()
	{
		if(saveMenuItem == null)
		{
			saveMenuItem = new JMenuItem("Save");
			myActionListener = 
				new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						VoicePad.this.repaint();
						if(fileChooser.showSaveDialog(VoicePad.this) == JFileChooser.APPROVE_OPTION)
						{
							File fSelected = fileChooser.getSelectedFile();
							try
							{
								FileWriter out = new FileWriter(fSelected);
								VoicePad.this.textArea.write(out);
								out.close();
							} 
							catch(IOException ioe) 
							{
								ioe.printStackTrace();
							}				
						}
						
						System.out.println("Java voicepad : Save menu item action performed."); 
					}
				};
			saveMenuItem.setMnemonic('s');
			saveMenuItem.addActionListener(myActionListener);
			
			System.out.println("Java voicepad : Save menu item created."); 
		}

		return saveMenuItem;
	}

	private JMenuItem getExitMenuItem()
	{
		if(exitMenuItem == null)
		{
			exitMenuItem = new JMenuItem("Exit");
			myActionListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						//closeSpeechSynthesisEngine();
						
						System.out.println("Java voicepad : Exit menu item action performed."); 
						setVisible(false);
						//System.exit(0);
					}
				};
			exitMenuItem.setMnemonic('x');
			exitMenuItem.addActionListener(myActionListener);

			System.out.println("Java voicepad : Exit menu item created."); 
		}
		
		return exitMenuItem;
	}
	// file menu and file menu items end

	// speech menu and speech menu items begin
	private JMenu getSpeechMenu()
	{
		if(speechMenu == null)
		{
			// create menu
			speechMenu = new JMenu("Speech");
			myActionListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						;

						System.out.println("Java voicepad : Speech menu action performed."); 
					}
				};
			speechMenu.setMnemonic('s');
			speechMenu.addActionListener(myActionListener);

			speechMenu.add(getPlayMenuItem());
			speechMenu.add(getPauseMenuItem());
			speechMenu.add(getResumeMenuItem());
			speechMenu.add(getStopMenuItem());

			System.out.println("Java voicepad : Speech menu created."); 
		}
		return speechMenu;
	}

	private JMenuItem getPlayMenuItem()
	{
		if(playMenuItem == null)
		{
			playMenuItem = new JMenuItem("Play");
			myActionListener =new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						String textToPlay = "";
		
						try
						{
							// retrieve the text to be played
							if(textArea.getSelectedText() != null)
								textToPlay = textArea.getSelectedText();
							else
								textToPlay = textArea.getText();

							// play the text
							synthesizer.speakPlainText(textToPlay, null);

							// wait till speaking is done
							//synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

							System.out.println("Java voicepad : Play menu item action performed."); 
						}
						catch(Exception e)
						{
							e.printStackTrace();
							System.out.println("Java voicepad : ERROR! Play menu item action." + e); 
						}
							
					}
				};
			playMenuItem.setMnemonic('p');
			playMenuItem.addActionListener(myActionListener);

			System.out.println("Java voicepad : Play menu item created."); 
		}
		return playMenuItem;
	}

	private JMenuItem getPauseMenuItem()
	{
		if(pauseMenuItem == null)
		{
			pauseMenuItem = new JMenuItem("Pause");
			myActionListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						try
						{
							// pause the speech synthesizer
							synthesizer.pause();

							System.out.println("Java voicepad : Pause menu item action performed."); 
						}
						catch(Exception e)
						{
							e.printStackTrace();
							System.out.println("Java voicepad : ERROR! Pause menu item action." + e); 
						}
					}
				};
			pauseMenuItem.setMnemonic('a');
			pauseMenuItem.addActionListener(myActionListener);

			System.out.println("Java voicepad : Pause menu item created."); 
		}
		return pauseMenuItem;
	}

	private JMenuItem getResumeMenuItem()
	{
		if(resumeMenuItem == null)
		{
			resumeMenuItem = new JMenuItem("Resume");
			myActionListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						try
						{
							// resume the speech synthesizer
							synthesizer.resume();
						}
						catch(Exception e)
						{
							e.printStackTrace();
							System.out.println("Java voicepad : ERROR! Resume menu item action." + e); 
						}
						
						System.out.println("Java voicepad : Resume menu item action performed."); 
					}
				};
			resumeMenuItem.setMnemonic('r');
			resumeMenuItem.addActionListener(myActionListener);

			System.out.println("Java voicepad : Resume menu item created."); 
		}
		return resumeMenuItem;
	}
	
	private JMenuItem getStopMenuItem()
	{
		if(stopMenuItem == null)
		{
			stopMenuItem = new JMenuItem("Stop");
			myActionListener = 
				new ActionListener()
				{
					public void actionPerformed(ActionEvent ae)
					{
						try
						{
							synthesizer.cancelAll();

							System.out.println("Java voicepad : Stop menu item action performed."); 
						}
						catch(Exception e)
						{
							e.printStackTrace();
							System.out.println("Java voicepad : ERROR! Stop menu item action." + e); 
						}
					}
				};
			stopMenuItem.setMnemonic('t');
			stopMenuItem.addActionListener(myActionListener);

			System.out.println("Java voicepad : Stop menu item created."); 
		}
		return stopMenuItem;
	}
	// speech menu and speech menu items end
}
