package de.tmasser.responsetemplates;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyActionListenerForDefaultsWindow implements ActionListener {
	private DefaultsWindow defaultsWindow;
	
	public MyActionListenerForDefaultsWindow(DefaultsWindow defaultsWindow) {
		this.defaultsWindow = defaultsWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "buttonNew" : this.actionButtonNew(); break;
			case "buttonSave" : this.actionButtonSave(); break;
			case "buttonCancel" : this.actionButtonCancel(); break;
		default: System.err.println("No actiond defined");
	}
	}

	private void actionButtonNew() {
		this.defaultsWindow.addNewLine();
	}

	private void actionButtonCancel() {
		this.close();
	}

	private void actionButtonSave() {
		defaultsWindow.saveEntry();
		this.close();
	}
	
	private void close() {
		this.defaultsWindow.setVisible(false);
		this.defaultsWindow.dispose();
	}

}
