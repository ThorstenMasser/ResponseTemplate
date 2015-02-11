package de.tmasser.responsetemplates;
import javax.swing.SwingUtilities;


public class Start {
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		SwingUtilities.invokeLater(mainWindow);
	}
}
