package de.tmasser.responsetemplates;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EntryWindow extends JFrame implements Runnable{
	private static final long serialVersionUID = 1039118717845849069L;
	private MultiLingualEntry entry;
	private String language;
	private JTextField textFieldTitle;
	private JTextField textFieldLanguage;
	private JTextArea textAreaBody;
	private MainWindow mainWindow;
	
	public EntryWindow(MainWindow mainWindow, MultiLingualEntry entry, String language) {
		this.entry = entry;
		this.language = language;
		this.mainWindow = mainWindow;
	}

	@Override
	public void run() {
		this.setSize(new Dimension(600,375));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("Catalog - Entry");
		this.textAreaBody = new JTextArea();
		this.textAreaBody.setWrapStyleWord(true);
		this.textAreaBody.setLineWrap(true);
		this.textAreaBody.setMargin(new Insets(10,10,10,10));
		this.textFieldTitle = new JTextField();
		if (this.entry != null) this.textFieldTitle.setText(this.entry.getTitle());
		this.textFieldLanguage = new JTextField();
		this.textFieldLanguage.setText(this.language);
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new GridLayout(2,2));
		panelTop.add(new JLabel("Language"), SwingConstants.CENTER);
		panelTop.add(new JLabel("Response Type"), SwingConstants.CENTER);
		panelTop.add(this.textFieldTitle);
		panelTop.add(this.textFieldLanguage);
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new FlowLayout());
		JButton buttonSave = new JButton("Save");
		JButton buttonCancel = new JButton("Cancel");
		buttonSave.setActionCommand("buttonSave");
		buttonCancel.setActionCommand("buttonCancel");
		ActionListener actionListener = new MyActionListenerForEntryWindow(this);
		buttonSave.addActionListener(actionListener );
		buttonCancel.addActionListener(actionListener );
		panelBottom.add(buttonSave);
		panelBottom.add(buttonCancel);
		if (this.entry != null) this.textAreaBody.setText(this.entry.getBody(this.language));
		this.add(panelTop, BorderLayout.NORTH);
		this.add(this.textAreaBody, BorderLayout.CENTER);
		this.add(panelBottom, BorderLayout.SOUTH);
		this.setVisible(true);	
	}

	public void saveEntry() {
		if (this.entry == null) {
			this.mainWindow.addEntry(this.textFieldTitle.getText(), this.textAreaBody.getText(), this.textFieldLanguage.getText());
		}
		else {
			this.entry.setTitle(this.textFieldTitle.getText());
			this.entry.setBody(this.textAreaBody.getText(), this.language);
			this.mainWindow.saveCatalog();
			this.mainWindow.updateTextarea();
			this.mainWindow.getComboTitles().setSelectedItem(this.entry);
			this.mainWindow.getComboLanguages().setSelectedItem(this.language);
		}
	}
}
