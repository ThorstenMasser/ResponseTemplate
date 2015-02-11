package de.tmasser.responsetemplates;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyActionListenerForEntryWindow implements ActionListener {
	private EntryWindow entryWindow;
	
	public MyActionListenerForEntryWindow(EntryWindow entryWindow) {
		this.entryWindow = entryWindow;
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
		entryWindow.saveEntry();
		this.close();
	}
	
	private void close() {
		this.entryWindow.setVisible(false);
		this.entryWindow.dispose();
	}

}
