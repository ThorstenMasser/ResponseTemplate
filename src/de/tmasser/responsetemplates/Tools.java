package de.tmasser.responsetemplates;

public class Tools {
	public static String realTrim(String text) {
		text = text.replace("\\p{Z}","");
    	text = text.replace("\t", " ");
    	while (text.contains("  ")) text = text.replace("  ", " ");
    	text = text.replace("\n ", "\n");
    	text = text.trim();
    	
    	return text;
	}
}
