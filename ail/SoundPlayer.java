package ail;

import java.io.File;

public class SoundPlayer extends Thread {
	private File soundFile;
	
	public SoundPlayer(String path) { 
		soundFile = new File(path);
		if(!soundFile.exists()) { 
			System.err.println("Wave file not found: " + path);
			return;
		}
	}
	
	public void play() {
		new SoundPlayerThread(soundFile).start();
	}
} 
