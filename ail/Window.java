package ail;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Window extends JFrame implements WindowListener {
	private Runnable closeHandler;
	
	public static void useWindowsTheme() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch(Exception exception) {
		}
	}
	
	public Window(String title, int width, int height) {
		useWindowsTheme();
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(width, height);
		setLocationRelativeTo(null);
		addWindowListener(this);
		closeHandler = null;
	}
	
	public void setCloseHandler(Runnable newCloseHandler) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		closeHandler = newCloseHandler;
	}
	
	public void visualise() {
		setVisible(true);
	}
	
	public String getString(String title, String description, String defaultString) {
		return (String)JOptionPane.showInputDialog(this, description, title, JOptionPane.PLAIN_MESSAGE, null, null, defaultString);
	}
	
	public String getString(String title, String description) {
		return getString(title, description, "");
	}
	
	public void windowActivated(WindowEvent event) {	
	}
	
	public void windowClosed(WindowEvent event) {
		//setVisible(false);
		if(closeHandler != null)
			closeHandler.run();
	}
	
	public void windowClosing(WindowEvent event) {
	}
	
	public void windowDeactivated(WindowEvent event) {	
	}
	
	public void windowDeiconified(WindowEvent event) {	
	}
	
	public void windowIconified(WindowEvent event) {	
	}
	
	public void windowOpened(WindowEvent event) {	
	}
}
