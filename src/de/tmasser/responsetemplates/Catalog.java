package de.tmasser.responsetemplates;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;


public class Catalog {
	private static final String SETTINGS_ENTRY = "Data Folder (under home directory)";
	private static final String DATA_FILENAME = "data_ml.xml";
	
	private ArrayList<MultiLingualEntry> entries;
	private MainWindow mainWindow;
	
	public Catalog(MainWindow mainWindow) {
		this.entries = new ArrayList<>();
		this.mainWindow = mainWindow;
		this.load();
	}
	
	public void load() {
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLStreamReader r;
		
		try {
			String filename;
			if ((this.mainWindow.getSetting(SETTINGS_ENTRY) == null) || (this.mainWindow.getSetting(SETTINGS_ENTRY).equals(""))) {
				filename = DATA_FILENAME;
			}
			else {
				filename = System.getProperty("user.home") + File.separator + URLDecoder.decode(this.mainWindow.getSetting(SETTINGS_ENTRY), "UTF-8") + File.separator + DATA_FILENAME; 
			}
			r = f.createXMLStreamReader(new FileReader(filename));
			while(r.hasNext()) {
			    r.next();
			    if ((r.isStartElement()) && (r.getName().toString().toLowerCase().equals("entry"))) {
			    	MultiLingualEntry entry = new MultiLingualEntry();
			    	entry.readNextXMLEntry(r);
			    	this.entries.add(entry);
		    	}
			}
			r.close();
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}

	public void save() {
		XMLOutputFactory f = XMLOutputFactory.newInstance();
		
		try {
			String filename;
			if (this.mainWindow.getSetting(SETTINGS_ENTRY) == "") {
				filename = DATA_FILENAME;
			}
			else {
				filename = System.getProperty("user.home") + File.separator + URLDecoder.decode(this.mainWindow.getSetting(SETTINGS_ENTRY), "UTF-8") + File.separator + DATA_FILENAME; 
			}
			
			XMLStreamWriter w = f.createXMLStreamWriter(new FileWriter(filename));
			w.writeStartDocument();
			w.writeCharacters("\n");
			w.writeStartElement("catalog");
			for(MultiLingualEntry entry : this.entries) {
				entry.writeXML(w);
			}
			w.writeEndElement();
			w.writeEndDocument();
			w.flush();
			w.close();
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getAllTitles() {
		ArrayList<String> list = new ArrayList<>();
		for(MultiLingualEntry entry : this.entries) {
			list.add(entry.getTitle());
		}
		return list;
	}

	public ArrayList<MultiLingualEntry> getAllEntries() {
		ArrayList<MultiLingualEntry> list = new ArrayList<>();
		for(MultiLingualEntry entry : this.entries) {
			list.add(entry);
		}
		return list;
	}

	public MultiLingualEntry add(String title, String body, String language) {
		MultiLingualEntry entryFound = null;
		
		for(MultiLingualEntry entry : this.entries) {
			if (entry.getTitle().equals(title)) {
				entryFound = entry;
				break;
			}
		}
		
		if (entryFound == null) {
			entryFound = new MultiLingualEntry(title, body, language);
			this.entries.add(entryFound);
		}
		else {
			entryFound.addEntry(body, language);
		}
		
		return entryFound;
	}

	public void delete(MultiLingualEntry entry, String language) {
		if (JOptionPane.showConfirmDialog(null, "Are you sure to delete the entry for language '" + language + "' ?", "Attention", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			entry.deleteLanguageEntry(language);
			if (entry.getAllLanguages().size() == 0) this.entries.remove(entry);
		}
	}

	public void deleteAll(MultiLingualEntry entry) {
		if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the complete entry '"+ entry.getTitle() +"' ?", "Attention", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			this.entries.remove(entry);
		}
	}

	public void reload() {
		this.entries = new ArrayList<>();
		this.load();
	}
}
