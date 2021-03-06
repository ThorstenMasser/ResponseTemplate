package de.tmasser.responsetemplates.windows;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.tmasser.responsetemplates.Catalog;
import de.tmasser.responsetemplates.Defaults;
import de.tmasser.responsetemplates.MultiLingualEntry;
import de.tmasser.responsetemplates.Settings;
import de.tmasser.responsetemplates.actionlisteners.MyActionListenerForMainWindow;


public class MainWindow extends JFrame implements Runnable{
	private static final long serialVersionUID = -3880026026104218593L;
	private Catalog catalog;
	private Defaults defaults;
	private Settings settings;
	private JComboBox<MultiLingualEntry> comboTitles;
	private JComboBox<String> comboLanguages;
	private JTextArea textarea;
	
	
	public MainWindow() {
		this.settings = new Settings();
		this.catalog = new Catalog(this);
		this.defaults = new Defaults();
	}

	@Override
	public void run() {
		MyActionListenerForMainWindow actionListener = new MyActionListenerForMainWindow(this);
		this.setSize(new Dimension(1200,800));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("Response Templates");
		this.setLocationRelativeTo(null);
		this.comboTitles = new JComboBox<MultiLingualEntry>();
		this.comboTitles.addActionListener(actionListener);
		this.comboTitles.setActionCommand("comboTitles");
		this.comboTitles.setEditable(false);
		this.comboLanguages = new JComboBox<>();
		this.comboLanguages.addActionListener(actionListener);
		this.comboLanguages.setActionCommand("comboLanguages");
		this.comboLanguages.setEditable(false);
		this.textarea = new JTextArea();
		this.textarea.setEditable(false);
		this.textarea.setMargin(new Insets(10,10,10,10));
		this.textarea.setLineWrap(true);
		this.textarea.setWrapStyleWord(true);
		JButton buttonUse = new JButton("Use");
		buttonUse.addActionListener(actionListener);
		buttonUse.setActionCommand("buttonUse");
		JButton buttonEdit = new JButton("Edit");
		buttonEdit.addActionListener(actionListener);
		buttonEdit.setActionCommand("buttonEdit");
		JButton buttonNew = new JButton("New");
		buttonNew.addActionListener(actionListener);
		buttonNew.setActionCommand("buttonNew");
		JButton buttonDelete = new JButton("Delete language entry");
		buttonDelete.addActionListener(actionListener);
		buttonDelete.setActionCommand("buttonDelete");
		JButton buttonDeleteAll = new JButton("Delete complete entry");
		buttonDeleteAll.addActionListener(actionListener);
		buttonDeleteAll.setActionCommand("buttonDeleteAll");
		JButton buttonDefaults = new JButton("Defaults");
		buttonDefaults.addActionListener(actionListener);
		buttonDefaults.setActionCommand("buttonDefaults");
		JButton buttonReload = new JButton("Reload");
		buttonReload.addActionListener(actionListener);
		buttonReload.setActionCommand("buttonReload");
		JButton buttonSettings = new JButton("Settings");
		buttonSettings.addActionListener(actionListener);
		buttonSettings.setActionCommand("buttonSettings");
		JButton buttonExit = new JButton("Exit");
		buttonExit.addActionListener(actionListener);
		buttonExit.setActionCommand("buttonExit");
		JButton buttonHelp = new JButton("?");
		buttonHelp.addActionListener(actionListener);
		buttonHelp.setActionCommand("buttonHelp");
		
		this.initComboTitles();
		JPanel panelCombos = new JPanel();
		panelCombos.add(this.comboTitles);
		panelCombos.add(this.comboLanguages);
		JPanel panelButtons = new JPanel();
		panelButtons.add(buttonUse);
		panelButtons.add(buttonEdit);
		panelButtons.add(buttonNew);
		panelButtons.add(buttonDelete);
		panelButtons.add(buttonDeleteAll);
		panelButtons.add(buttonDefaults);
		panelButtons.add(buttonReload);
		panelButtons.add(buttonSettings);
		panelButtons.add(buttonExit);
		panelButtons.add(buttonHelp);
		this.updateTextarea();
		this.getContentPane().add(panelCombos, BorderLayout.NORTH);
		this.getContentPane().add(textarea,BorderLayout.CENTER);
		this.getContentPane().add(panelButtons, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	public void initComboTitles() {
		DefaultComboBoxModel<MultiLingualEntry> comboBoxModel = new DefaultComboBoxModel<>();
		this.comboTitles.setModel(comboBoxModel);
		for(MultiLingualEntry entry : this.catalog.getAllEntries()) {
			comboBoxModel.addElement(entry);
		}
		if (this.catalog.getAllEntries().size() > 0) {
			this.comboTitles.setSelectedIndex(0);
			this.initComboLanguage();
		}
		
	}
	
	public void initComboLanguage() {
		MultiLingualEntry entry=(MultiLingualEntry) this.comboTitles.getSelectedItem();
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		this.comboLanguages.setModel(comboBoxModel);
		for(String language : entry.getAllLanguages()) {
			comboBoxModel.addElement(language);
		}
		this.comboLanguages.setSelectedIndex(0);
	}

	public JComboBox<String> getComboLanguages() {
		return this.comboLanguages;
	}

	public JComboBox<MultiLingualEntry> actionComboTitles() {
		return this.comboTitles;
	}

	public JTextArea getTextArea() {
		return this.textarea;
	}

	public JComboBox<MultiLingualEntry> getComboTitles() {
		return this.comboTitles;
	}

	public void updateTextarea() {
		if (this.comboTitles.getSelectedItem() != null)
			this.textarea.setText(((MultiLingualEntry)this.comboTitles.getSelectedItem()).getBody(((String)this.comboLanguages.getSelectedItem())));		
	}

	public Defaults getDefaults() {
		return this.defaults;
	}

	public void addEntry(String title, String body, String language) {
		MultiLingualEntry entry = this.catalog.add(title, body, language, true);
		this.initComboTitles();
		this.initComboLanguage();
		this.comboTitles.setSelectedItem(entry);
		this.comboLanguages.setSelectedItem(language);
	}

	public void delete() {
		MultiLingualEntry entry = (MultiLingualEntry)this.comboTitles.getSelectedItem();
		this.catalog.delete(entry, (String)this.comboLanguages.getSelectedItem());
		this.initComboTitles();
		this.initComboLanguage();
		this.comboTitles.setSelectedItem(entry);
	}

	public void deleteAll() {
		this.catalog.deleteAll((MultiLingualEntry)this.comboTitles.getSelectedItem());
		this.initComboTitles();
		this.initComboLanguage();
	}

	public Settings getSettings() {
		return this.settings;
	}

	public void reload() {
		this.defaults.reload();
		this.settings.reload();
		this.catalog.reload();
		this.initComboTitles();
		this.initComboLanguage();
	}

	public String getSetting(String settingsEntry) {
		return this.settings.get(settingsEntry);
	}

	public void saveEntry(MultiLingualEntry entry, String language) {
		this.catalog.saveEntry(entry, language);
	}
}
