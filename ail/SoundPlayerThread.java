package ail;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class SoundPlayerThread extends Thread {
	private final int bufferSize = 128 * 1024;
	
	private File soundFile;
	
	public SoundPlayerThread(File soundFile) {
		this.soundFile = soundFile;
	}
	
	public void run() {
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		}
		catch(Exception exception) {
			exception.printStackTrace();
			return;
		}
		
		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine dataLine = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try { 
			dataLine = (SourceDataLine)AudioSystem.getLine(info);
			dataLine.open(format);
		}
		catch(Exception exception) { 
			exception.printStackTrace();
			return;
		}  
 
		dataLine.start();
		int bytesRead = 0;
		byte[] audioBuffer = new byte[bufferSize];
 
		try { 
			while (bytesRead != -1) { 
				bytesRead = audioInputStream.read(audioBuffer, 0, audioBuffer.length);
				if (bytesRead >= 0) 
					dataLine.write(audioBuffer, 0, bytesRead);
			}
		}
		catch(IOException exception) { 
			exception.printStackTrace();
			return;
		}
		finally {
			dataLine.drain();
			dataLine.close();
		}
	} 
}
