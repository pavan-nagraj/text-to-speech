

import javax.swing.*;       
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Enumeration;
import java.util.Locale;

import javax.speech.*;
import javax.speech.synthesis.*;

public class TTS
{
    TTS(){
    	
    }
	public void speakTheText(String speech){
		//listAllVoices("general");
		String voiceName = "kevin16";
		System.out.println();
		System.out.println("Using voice: " +voiceName);
		try
		{
		SynthesizerModeDesc desc = new SynthesizerModeDesc(null, "general", Locale.US, null, null);
		Synthesizer synthesizer = Central.createSynthesizer(desc);
		if(synthesizer == null)
		{
		System.err.println(noSynthesizerMessage());
		System.exit(1);
		}
		synthesizer.allocate();


		synthesizer.resume();
		desc = (SynthesizerModeDesc)synthesizer.getEngineModeDesc();
		Voice voices[] = desc.getVoices();

		Voice voice = null;
		int i = 0;
		do
		{
		if(i >= voices.length)
		break;
		if(voices[i].getName().equals(voiceName))
		{
		voice = voices[i];

		break;
		}
		i++;
		} while(true);
		if(voice == null)
		{
		System.err.println("Synthesizer does not have a voice named " +voiceName+ ".");
		System.exit(1);
		}
		synthesizer.getSynthesizerProperties().setVoice(voice);
		synthesizer.speakPlainText(speech, null);
		synthesizer.waitEngineState(0x10000L);
		//synthesizer.deallocate();
		//System.exit(0);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
	}
	private static String noSynthesizerMessage()
	{
	String message = "No synthesizer created. This may be the result of any\nnumber of problems. It's typically due to a missing\n\"speech.properties\" file that should be at either of\nthese locations: \n\n";
	message = message +"user.home : " +System.getProperty("user.home") +"\n";
	message = message +"java.home/lib: " +System.getProperty("java.home")+ File.separator +"lib\n\n"+ "Another cause of this problem might be corrupt or missing\n" +"voice jar files in the freetts lib directory. This problem\n" +"also sometimes arises when the freetts.jar file is corrupt\n" +"or missing. Sorry about that. Please check for these\n" +"various conditions and then try again.\n";
	return message;
	}

}
