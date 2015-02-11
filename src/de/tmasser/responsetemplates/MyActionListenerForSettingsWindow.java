package de.tmasser.responsetemplates;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyActionListenerForSettingsWindow implements ActionListener {
	private SettingsWindow settingsWindow;
	
	public MyActionListenerForSettingsWindow(SettingsWindow defaultsWindow) {
		this.settingsWindow = defaultsWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "buttonSave" : this.actionButtonSave(); break;
			case "buttonCancel" : this.actionButtonCancel(); break;
		default: System.err.println("No actiond defined");
	}
	}

	private void actionButtonCancel() {
		this.close();
	}

	private void actionButtonSave() {
		settingsWindow.saveEntry();
		this.settingsWindow.reload();
		this.close();
	}
	
	private void close() {
		this.settingsWindow.setVisible(false);
		this.settingsWindow.dispose();
	}

}
