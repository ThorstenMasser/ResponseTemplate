package de.tmasser.responsetemplates;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;


public class MultiLingualEntry {
	private String title;
	private HashMap<String, String> body;
	
	public MultiLingualEntry(String title, String body, String language) {
		this();
		this.title = title;
		this.body.put(language, body);
	}

	public MultiLingualEntry() {
		this.body = new HashMap<String, String>();
	}

	public String getTitle() {
		return this.title;
	}

	public String getBody(String language) {
		return this.body.get(language);
	}
	
	public void setTitle(String title) {
		this.title= title;
	}

	public void setBody(String body, String language) {
		this.body.put(language, body);
	}
	
	public void setEntry(String title, String body, String language) {
		this.setTitle(title);
		this.setBody(body, language);
	}
	
	public void addLanguage(String body, String language) {
		this.setBody(body, language);
	}
	
	public void deleteLanguageEntry(String language) {
		this.body.remove(language);
	}

	@Override
	public String toString() {
		return this.title;
	}

	public String getBodyWithIndention(int numberOfSpaces, String language) {
		String spaces = "";
		if (this.body.get(language) != null) {
			for(int i=0; i< numberOfSpaces; i++) spaces += " ";
			
			return this.body.get(language).replace("\n", "\n" + spaces);
		}
		return spaces;
	}
	
	public void writeXML(XMLStreamWriter streamWriter) throws XMLStreamException {
		Iterator<String> langIt = this.body.keySet().iterator();
		streamWriter.writeCharacters("\n\t");
		streamWriter.writeStartElement("entry");
		streamWriter.writeCharacters("\n\t\t");
		streamWriter.writeStartElement("title");
		streamWriter.writeCharacters("\n\t\t\t");
		streamWriter.writeCharacters(this.title);
		streamWriter.writeCharacters("\n\t\t");
		streamWriter.writeEndElement();
		while (langIt.hasNext()) {
			String lang = langIt.next();
			streamWriter.writeCharacters("\n\t\t");
			streamWriter.writeStartElement("body");
			streamWriter.writeAttribute("lang", lang);
			streamWriter.writeCharacters("\n\t\t\t");
			streamWriter.writeCharacters(this.addIndentionsToBody(this.body.get(lang),12));
			streamWriter.writeCharacters("\n\t\t");
			streamWriter.writeEndElement();
		}
		streamWriter.writeCharacters("\n\t");
		streamWriter.writeEndElement();
		streamWriter.writeCharacters("\n");
	}
	
	public void readNextXMLEntry(XMLStreamReader streamReader) throws XMLStreamException  {
		String title = "", body = "", language = null;
		
		while (streamReader.hasNext()) {
			streamReader.next();
			if ((streamReader.isEndElement()) && (streamReader.getName().toString().toLowerCase().equals("entry"))) return;
			if ((streamReader.isStartElement()) && (streamReader.getName().toString().toLowerCase().equals("title"))) title = Tools.realTrim(streamReader.getElementText());
			if ((streamReader.isStartElement()) && (streamReader.getName().toString().toLowerCase().equals("body"))) {
				language = streamReader.getAttributeValue("","lang");
				body = Tools.realTrim(streamReader.getElementText());
			}
			
			if (!title.equals("") && !body.equals("") && language != null) {
				this.title = title;
				this.body.put(language, body);
				body = "";
				language=null;
			}
		}
	}
	
	public Set<String> getAllLanguages() {
		return this.body.keySet();
	}
	
	public String addIndentionsToBody(String body, int numberOfSpaces) {
		String spaces = "";
		for(int i=0; i< numberOfSpaces; i++) spaces += " ";
		
		return body.replace("\n", "\n" + spaces);
	}

	public void addEntry(String body, String language) {
		this.body.put(language,  body);
	}
}
