package de.tmasser.responsetemplates;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class Settings extends HashMap<String, String> {
	
	private static final long serialVersionUID = 553295214406777433L;
	private static final String SETTINGS_FILENAME = "settings.xml";
	
	public Settings() {
		this.load();
	}
	
	public void load() {
		XMLInputFactory f = XMLInputFactory.newInstance();
		XMLStreamReader r;
		String key = null,value = null;
		try {
			r = f.createXMLStreamReader(new FileReader(SETTINGS_FILENAME));
			
			while(r.hasNext()) {
				r.next();
				
				if ((r.isStartElement()) && (r.getName().toString().toLowerCase().equals("key"))) key = r.getElementText();
				if ((r.isStartElement()) && (r.getName().toString().toLowerCase().equals("value"))) value = r.getElementText();
				if (key != null && value != null) {
					this.put(Tools.realTrim(key), Tools.decrypt(Tools.realTrim(value)));
					key = null;
					value = null;
				}
			}
			r.close();
		} catch (FileNotFoundException | XMLStreamException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void save() {
		XMLOutputFactory f = XMLOutputFactory.newInstance();
		try {
			XMLStreamWriter w = f.createXMLStreamWriter(new FileWriter(SETTINGS_FILENAME));
			w.writeStartDocument();
			w.writeCharacters("\n");
			w.writeStartElement("settings");
			w.writeCharacters("\n");
			Iterator<String> it = this.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = Tools.encrypt(this.get(key));
				w.writeCharacters("    ");
				w.writeStartElement("entry");
				w.writeCharacters("\n        ");
				w.writeStartElement("key");
				w.writeCharacters("\n            ");
				w.writeCharacters(key);
				w.writeCharacters("\n        ");
				w.writeEndElement();
				w.writeCharacters("\n        ");
				w.writeStartElement("value");
				w.writeCharacters("\n            ");
				w.writeCharacters(value);
				w.writeCharacters("\n       ");
				w.writeEndElement();
				w.writeCharacters("\n    ");
				w.writeEndElement();
				w.writeCharacters("\n");
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

	public void reload() {
		this.clear();
		this.load();
	}
}
