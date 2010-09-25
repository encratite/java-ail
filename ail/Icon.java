package ail;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Icon {
	private static Map<String, ImageIcon> iconMap = new HashMap<String, ImageIcon>();
	
	public ImageIcon icon;
	
	public Icon(String path) {
		synchronized(iconMap) {
			if(iconMap.containsKey(path))
				icon = iconMap.get(path);
			else {
				icon = new ImageIcon(path);
				iconMap.put(path, icon);
			}
		}
	}
	
	public void resize(int width, int height) {
	   Image input = icon.getImage();  
	   Image output = input.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);  
	   icon = new ImageIcon(output);  
	}
}
