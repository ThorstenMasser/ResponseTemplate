package de.tmasser.responsetemplates;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

import de.tmasser.responsetemplates.windows.MainWindow;


public class Catalog {
	public String spreadsheet_url;	
	private ArrayList<MultiLingualEntry> entries;
	private MainWindow mainWindow;
	
	public Catalog(MainWindow mainWindow) {
		this.entries = new ArrayList<>();
		this.mainWindow = mainWindow;
		this.spreadsheet_url = "https://spreadsheets.google.com/feeds/spreadsheets/" + this.mainWindow.getSetting("Spreadsheet ID");
		this.load();
	}
	
	public void load() {
		SpreadsheetService service = new SpreadsheetService("Response Template");
		try {
			service.setUserCredentials(this.mainWindow.getSetting("Username"), this.mainWindow.getSetting("Password"));
			URL metafeedUrl = new URL(this.spreadsheet_url);
		    SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl, SpreadsheetEntry.class);
		    URL listFeedUrl = ((WorksheetEntry) spreadsheet.getWorksheets().get(0)).getListFeedUrl();
		    ListFeed feed = (ListFeed) service.getFeed(listFeedUrl, ListFeed.class);
		    String title = "";
		    String lang = "";
		    String text = "";
		    for(ListEntry entry : feed.getEntries()) {
		    	for(String tag : entry.getCustomElements().getTags()) {
		    		if (tag.equals("title")) title = entry.getCustomElements().getValue(tag);
		    		if (tag.equals("language")) lang = entry.getCustomElements().getValue(tag);
		    		if (tag.equals("text")) text = entry.getCustomElements().getValue(tag);
		    	}
		    	this.add(title, text, lang);
		    	title = "";
		    	lang = "";
		    	text = "";
		    }
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void saveEntry(MultiLingualEntry entry, String language) {
		SpreadsheetService service = new SpreadsheetService("Response Template");
		try {
			String username = this.mainWindow.getSetting("Username");
			String password = this.mainWindow.getSetting("Password");
			service.setUserCredentials(username, password);
			URL metafeedUrl = new URL(this.spreadsheet_url);
		    SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl, SpreadsheetEntry.class);
		    URL listFeedUrl = ((WorksheetEntry) spreadsheet.getWorksheets().get(0)).getListFeedUrl();
		    ListFeed feed = (ListFeed) service.getFeed(listFeedUrl, ListFeed.class);
		    String title = "";
		    String lang = "";
		    String text = "";
		    String user = "";
		    Boolean found = false;
		    for(ListEntry existingEntry : feed.getEntries()) {
		    	for(String tag : existingEntry.getCustomElements().getTags()) {
		    		if (tag.equals("title")) title = existingEntry.getCustomElements().getValue(tag);
		    		if (tag.equals("language")) lang = existingEntry.getCustomElements().getValue(tag);
		    		if (tag.equals("text")) text = existingEntry.getCustomElements().getValue(tag);
		    	}
		    	if (title.equals(entry.getTitle()) && lang.equals(language)) {
		    		existingEntry.getCustomElements().setValueLocal("text",entry.getBody(lang));
		    		existingEntry.getCustomElements().setValueLocal("username",username);
		    		existingEntry.update();
		    		found = true;
		    		break;
		    	}
		    	title = "";
		    	lang = "";
		    	text = "";
		    	user = "";
		    }
		    if (!found) {
		    	ListEntry row = new ListEntry();
			    row.getCustomElements().setValueLocal("title", entry.getTitle());
			    row.getCustomElements().setValueLocal("language", language);
			    row.getCustomElements().setValueLocal("text", entry.getBody(language));
			    row.getCustomElements().setValueLocal("username", username);
			    row = service.insert(listFeedUrl, row);
		    }
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
		}
	}

	private void deleteLanguageEntry(MultiLingualEntry entry, String language) {
		SpreadsheetService service = new SpreadsheetService("Response Template");
		try {
			String username = this.mainWindow.getSetting("Username");
			String password = this.mainWindow.getSetting("Password");
			service.setUserCredentials(username, password);
			URL metafeedUrl = new URL(spreadsheet_url);
		    SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl, SpreadsheetEntry.class);
		    URL listFeedUrl = ((WorksheetEntry) spreadsheet.getWorksheets().get(0)).getListFeedUrl();
		    ListFeed feed = (ListFeed) service.getFeed(listFeedUrl, ListFeed.class);
		    String title = "";
		    String lang = "";
		    String text = "";
		    String user = "";
		    for(ListEntry existingEntry : feed.getEntries()) {
		    	for(String tag : existingEntry.getCustomElements().getTags()) {
		    		if (tag.equals("title")) title = existingEntry.getCustomElements().getValue(tag);
		    		if (tag.equals("language")) lang = existingEntry.getCustomElements().getValue(tag);
		    		if (tag.equals("text")) text = existingEntry.getCustomElements().getValue(tag);
		    	}
		    	if (title.equals(entry.getTitle()) && lang.equals(language)) {
		    		existingEntry.delete();
		    		break;
		    	}
		    	title = "";
		    	lang = "";
		    	text = "";
		    	user = "";
		    }
		} catch (IOException | ServiceException e) {
			e.printStackTrace();
		}
	}

	private void deleteEntry(MultiLingualEntry entry) {
		SpreadsheetService service = new SpreadsheetService("Print Google Spreadsheet Demo");
		try {
			String username = this.mainWindow.getSetting("Username");
			String password = this.mainWindow.getSetting("Password");
			service.setUserCredentials(username, password);
			URL metafeedUrl = new URL(this.spreadsheet_url);
		    SpreadsheetEntry spreadsheet = service.getEntry(metafeedUrl, SpreadsheetEntry.class);
		    URL listFeedUrl = ((WorksheetEntry) spreadsheet.getWorksheets().get(0)).getListFeedUrl();
		    ListFeed feed = (ListFeed) service.getFeed(listFeedUrl, ListFeed.class);
		    String title = "";
		    String lang = "";
		    String text = "";
		    String user = "";
		    for(ListEntry existingEntry : feed.getEntries()) {
		    	for(String tag : existingEntry.getCustomElements().getTags()) {
		    		if (tag.equals("title")) title = existingEntry.getCustomElements().getValue(tag);
		    		if (tag.equals("language")) lang = existingEntry.getCustomElements().getValue(tag);
		    		if (tag.equals("text")) text = existingEntry.getCustomElements().getValue(tag);
		    	}
		    	if (title.equals(entry.getTitle())) {
		    		existingEntry.delete();
		    	}
		    	title = "";
		    	lang = "";
		    	text = "";
		    	user = "";
		    }
		} catch (IOException | ServiceException e) {
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
		return this.add(title, body, language, false);
	}
	
	public MultiLingualEntry add(String title, String body, String language, boolean save) {
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
		if (save) {
			this.saveEntry(entryFound, language);
			System.out.println("Added");
		}
		
		return entryFound;
	}

	public void delete(MultiLingualEntry entry, String language) {
		if (JOptionPane.showConfirmDialog(null, "Are you sure to delete the entry for language '" + language + "' ?", "Attention", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			this.deleteLanguageEntry(entry, language);
			entry.deleteLanguageEntry(language);
			if (entry.getAllLanguages().size() == 0) {
				System.out.println("Delete whole entry");
				this.entries.remove(entry);
			}
			
		}
	}
	

	public void deleteAll(MultiLingualEntry entry) {
		if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the complete entry '"+ entry.getTitle() +"' ?", "Attention", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			this.deleteEntry(entry);
			this.entries.remove(entry);
		}
	}

	public void reload() {
		this.entries = new ArrayList<>();
		this.load();
	}
}
