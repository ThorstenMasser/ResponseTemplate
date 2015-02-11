package de.tmasser.responsetemplates;
import javax.swing.SwingUtilities;

import de.tmasser.responsetemplates.windows.MainWindow;


public class Start {
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		SwingUtilities.invokeLater(mainWindow);
	}
}
