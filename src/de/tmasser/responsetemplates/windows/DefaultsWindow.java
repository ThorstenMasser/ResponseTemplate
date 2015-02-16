package de.tmasser.responsetemplates.windows;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.tmasser.responsetemplates.Defaults;
import de.tmasser.responsetemplates.actionlisteners.MyActionListenerForDefaultsWindow;


public class DefaultsWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = -2565216451893426188L;
	private MainWindow mainWindow;
	private JPanel defaultsPanel;

	public DefaultsWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.defaultsPanel = this.getDefaultsPanel();
	}
	
	@Override
	public void run() {
		this.setSize(new Dimension(320,200));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Response Templates - Defaults");
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		JLabel label = new JLabel("Variable");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);
		label = new JLabel("Value");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);
		this.add(panel, BorderLayout.NORTH);
		JScrollPane scroll = new JScrollPane(this.defaultsPanel);
		this.add(scroll, BorderLayout.CENTER);
		MyActionListenerForDefaultsWindow actionListener = new MyActionListenerForDefaultsWindow(this);
		panel = new JPanel();
		JButton buttonNew = new JButton("New");
		buttonNew.addActionListener(actionListener);
		buttonNew.setActionCommand("buttonNew");
		JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener(actionListener);
		buttonSave.setActionCommand("buttonSave");
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(actionListener);
		buttonCancel.setActionCommand("buttonCancel");
		panel.add(buttonSave);
		panel.add(buttonNew);
		panel.add(buttonCancel);
		this.add(panel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	private JPanel getDefaultsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		Defaults defaults = this.mainWindow.getDefaults();
		Iterator<String> it = defaults.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = defaults.get(key);
			panel.add(new JTextField(key));
			panel.add(new JTextField(value));
		}
		return panel;
	}

	public void saveEntry() {
		Defaults defaults = this.mainWindow.getDefaults();
		defaults.clear();
		for(int i=0;i<this.defaultsPanel.getComponentCount();i+=2) {
			String key = ((JTextField)this.defaultsPanel.getComponent(i)).getText();
			String value = ((JTextField)this.defaultsPanel.getComponent(i+1)).getText();
			if (!key.equals("") && !value.equals("")) {
				defaults.put(key, value);
			}
		}
		defaults.save();
	}

	public void addNewLine() {
		this.defaultsPanel.add(new JTextField());
		this.defaultsPanel.add(new JTextField());
		this.defaultsPanel.revalidate();
	}

}
