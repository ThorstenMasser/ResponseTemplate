package de.tmasser.responsetemplates.actionlisteners;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.tmasser.responsetemplates.MultiLingualEntry;
import de.tmasser.responsetemplates.windows.DefaultsWindow;
import de.tmasser.responsetemplates.windows.EntryWindow;
import de.tmasser.responsetemplates.windows.HelpWindow;
import de.tmasser.responsetemplates.windows.MainWindow;
import de.tmasser.responsetemplates.windows.SettingsWindow;

public class MyActionListenerForMainWindow implements ActionListener {

	private MainWindow mainWindow;
	
	public MyActionListenerForMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "buttonUse" : this.actionButtonUse(); break;
			case "buttonNew" : this.actionButtonNew(); break;
			case "buttonEdit" : this.actionButtonEdit(); break;
			case "buttonDelete" : this.actionButtonDelete(); break;
			case "buttonDeleteAll" : this.actionButtonDeleteAll(); break;
			case "buttonDefaults" : this.actionButtonDefaults(); break;
			case "buttonSettings" : this.actionButtonSettings(); break;
			case "buttonExit" : this.actionButtonExit(); break;
			case "comboTitles" : this.actionComboTitles(); break;
			case "comboLanguages" : this.actionComboLanguages(); break;
			case "buttonHelp" : this.actionButtonHelp(); break;
			default: System.err.println("No actiond defined");
		}
	}

	private void actionButtonExit() {
		System.exit(0);
	}

	private void actionButtonUse() {
		String text = ((MultiLingualEntry)mainWindow.getComboTitles().getSelectedItem()).getBody((String) mainWindow.getComboLanguages().getSelectedItem());
		String s = "";
		while(text.contains("[") && s != null) {
			int start = text.indexOf('[');
			int end = text.indexOf(']', start) + 1;
			String macroTitle = text.substring(start, end).toLowerCase();
			String prepopulation = "";
			if (this.mainWindow.getDefaults().containsKey(macroTitle.substring(1, macroTitle.length()-1))) {
				prepopulation = this.mainWindow.getDefaults().get(macroTitle.substring(1, macroTitle.length()-1));
			}
			s = (String)JOptionPane.showInputDialog(null,"Replace " + macroTitle + " with:\n", "Macro Replacement", JOptionPane.PLAIN_MESSAGE, null, null, prepopulation);
			if (s != null) text = text.replace(macroTitle, s);
		}
		if (s != null) {
			Toolkit.getDefaultToolkit ().getSystemClipboard().setContents (new StringSelection (text), null);
			JOptionPane.showMessageDialog(null, "Text copied to clipboard",null,JOptionPane.PLAIN_MESSAGE);
		}
		
	}

	public void actionComboLanguages() {
		this.mainWindow.updateTextarea();
	}

	private void actionComboTitles() {
		mainWindow.initComboLanguage();
	}

	private void actionButtonDefaults() {
		DefaultsWindow defaultWindow = new DefaultsWindow(this.mainWindow);
		SwingUtilities.invokeLater(defaultWindow);
	}

	private void actionButtonDelete() {
		this.mainWindow.delete();
	}

	private void actionButtonDeleteAll() {
		this.mainWindow.deleteAll();
	}

	private void actionButtonEdit() {
		EntryWindow entryWindow = new EntryWindow(this.mainWindow, (MultiLingualEntry)mainWindow.getComboTitles().getSelectedItem(), (String)mainWindow.getComboLanguages().getSelectedItem());
		SwingUtilities.invokeLater(entryWindow);
	}

	private void actionButtonNew() {
		EntryWindow entryWindow = new EntryWindow(this.mainWindow, null, null);
		SwingUtilities.invokeLater(entryWindow);
	}

	private void actionButtonSettings() {
		SettingsWindow settingsWindow = new SettingsWindow(this.mainWindow);
		SwingUtilities.invokeLater(settingsWindow);
	}
	
	private void actionButtonHelp() {
		HelpWindow helpWindow = new HelpWindow(this.mainWindow);
		SwingUtilities.invokeLater(helpWindow);
	}
}
