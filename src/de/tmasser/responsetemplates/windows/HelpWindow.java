package de.tmasser.responsetemplates.windows;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.tmasser.responsetemplates.Defaults;
import de.tmasser.responsetemplates.actionlisteners.MyActionListenerForDefaultsWindow;
import de.tmasser.responsetemplates.actionlisteners.MyActionListenerForHelpWindow;


public class HelpWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = -2565216451893426188L;
	private MainWindow mainWindow;

	public HelpWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void run() {
		this.setSize(new Dimension(640,480));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Response Templates - Help");
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		MyActionListenerForHelpWindow actionListener = new MyActionListenerForHelpWindow(this);
		JPanel panel = new JPanel();
		JTextArea textAreaBody = new JTextArea();
		textAreaBody.setWrapStyleWord(true);
		textAreaBody.setLineWrap(true);
		textAreaBody.setMargin(new Insets(10,10,10,10));
		try
        {
            FileReader reader = new FileReader( "readme.txt" );
            BufferedReader br = new BufferedReader(reader);
            textAreaBody.read( br, null );
            br.close();
        }
        catch(Exception e2) { 
        	System.out.println(e2); 
        	}
		JScrollPane scroll = new JScrollPane(textAreaBody);
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(actionListener);
		buttonClose.setActionCommand("buttonClose");
		panel.add(buttonClose);
		this.add(scroll, BorderLayout.CENTER);
		this.add(panel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
}
