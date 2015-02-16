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

import de.tmasser.responsetemplates.Settings;
import de.tmasser.responsetemplates.Tools;
import de.tmasser.responsetemplates.actionlisteners.MyActionListenerForSettingsWindow;


public class SettingsWindow extends JFrame implements Runnable {
	private static final long serialVersionUID = -2565216451893426183L;
	private MainWindow mainWindow;
	private JPanel defaultsPanel;

	public SettingsWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.defaultsPanel = this.getSettingsPanel();
	}
	
	@Override
	public void run() {
		this.setSize(new Dimension(800,200));
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
		MyActionListenerForSettingsWindow actionListener = new MyActionListenerForSettingsWindow(this);
		panel = new JPanel();
		JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener(actionListener);
		buttonSave.setActionCommand("buttonSave");
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(actionListener);
		buttonCancel.setActionCommand("buttonCancel");
		panel.add(buttonSave);
		panel.add(buttonCancel);
		this.add(panel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	private JPanel getSettingsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		Settings settings = this.mainWindow.getSettings();
		Iterator<String> it = settings.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = settings.get(key);
			JTextField keyField = new JTextField(key);
			keyField.setEditable(false);
			JTextField valueField = new JTextField(value);
			panel.add(keyField);
			panel.add(valueField);
		}
		return panel;
	}

	public void saveEntry() {
		Settings settings = this.mainWindow.getSettings();
		settings.clear();
		for(int i=0;i<this.defaultsPanel.getComponentCount();i+=2) {
			String key = ((JTextField)this.defaultsPanel.getComponent(i)).getText();
			String value = ((JTextField)this.defaultsPanel.getComponent(i+1)).getText();
			if (!key.equals("")) {
				settings.put(Tools.realTrim(key), Tools.realTrim(value));
			}
		}
		settings.save();
	}

	public void reload() {
		this.mainWindow.reload();
	}
}
