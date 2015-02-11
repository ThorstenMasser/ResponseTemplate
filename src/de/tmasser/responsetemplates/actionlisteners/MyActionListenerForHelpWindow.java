package de.tmasser.responsetemplates.actionlisteners;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.tmasser.responsetemplates.windows.DefaultsWindow;
import de.tmasser.responsetemplates.windows.HelpWindow;


public class MyActionListenerForHelpWindow implements ActionListener {
	private HelpWindow helpWindow;
	
	public MyActionListenerForHelpWindow(HelpWindow helpWindow) {
		this.helpWindow = helpWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "buttonClose" : this.actionButtonClose(); break;
			default: System.err.println("No actiond defined");
		}
	}

	private void actionButtonClose() {
		this.close();
	}
	
	private void close() {
		this.helpWindow.setVisible(false);
		this.helpWindow.dispose();
	}

}
