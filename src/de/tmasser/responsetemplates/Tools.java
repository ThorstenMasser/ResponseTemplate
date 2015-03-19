package de.tmasser.responsetemplates;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Tools {
	private static final byte[] keyValue = new byte[] { '!', 'M', 'y', ' ', 's','E', 'c', 'R', 'e', 'T', ' ', 'k', 'E', 'y', '!', '!'};
	
	public static String realTrim(String text) {
		text = text.replace("\\p{Z}","");
    	text = text.replace("\t", " ");
    	while (text.contains("  ")) text = text.replace("  ", " ");
    	text = text.replace("\n ", "\n");
    	text = text.trim();
    	
    	return text;
	}

	public static String encrypt(String message) {
		String encMessage = "";
		try {
			Key key = new SecretKeySpec(Tools.keyValue, "AES");
			Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal = c.doFinal(message.getBytes());
			encMessage = new BASE64Encoder().encode(encVal);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			return message;
		}
		return encMessage;
	}

	public static String decrypt(String message) {
		Key key = new SecretKeySpec(keyValue, "AES");
		String decMessage = "";
		try {
			Cipher c = Cipher.getInstance("AES");
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = new BASE64Decoder().decodeBuffer(message);
			byte[] decValue = c.doFinal(decordedValue);
			decMessage = new String(decValue);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | IOException e) {
			return message;
		}
		return decMessage;
	}
}
