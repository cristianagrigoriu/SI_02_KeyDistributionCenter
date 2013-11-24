import javax.crypto.Cipher;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.lang.*;
import java.nio.charset.Charset;

import javax.crypto.SecretKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Crypt3DES {

	SecretKey key;
	Cipher desCipher;
	AlgorithmParameters parameters;
	
	/*constructor*/
	public Crypt3DES(SecretKey key) {
		this.key = key;
		try {
			this.desCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0};//, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		    IvParameterSpec ivspec = new IvParameterSpec(iv);
			
			
			desCipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
			this.parameters = desCipher.getParameters();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
        	return;
		}
		catch (NoSuchPaddingException e) {
			e.printStackTrace();
        	return;
		}
		catch (InvalidKeyException e) {
        	e.printStackTrace();
        	return;
        }
		catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
        	return;
		}
	}
	
	/*cripteaza mesajul message cu cheia key date in constructor*/
	public String encryptMessage(String message) {
		try {
			/*crypting the text with 3DES*/
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0};//, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		    IvParameterSpec ivspec = new IvParameterSpec(iv);
			
			
			//desCipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
			//this.parameters = desCipher.getIV();
			byte[] byteDataToEncrypt = message.getBytes();
			byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt);
			String strCipherText = new BASE64Encoder().encode(byteCipherText);
			//System.out.println("Cipher Text generated using 3DES with CBC mode and PKCS5 Padding is: " + strCipherText);
			
			return strCipherText;
		}
        /*catch (InvalidKeyException e) {
        	e.printStackTrace();
        	return null;
        }*/
        catch (IllegalBlockSizeException e) {
        	e.printStackTrace();
        	return null;
        }
        catch(BadPaddingException e) {
        	e.printStackTrace();
        	return null;
        }
		
		
	}

	public String decryptMessage2(String message) {
		return message;
	}
	
	/**/
	public String decryptMessage(String message) {
		try {
			desCipher.init(Cipher.DECRYPT_MODE, key, this.parameters/*desCipher.getParameters()*/);
			
			/*byte[] data = new byte[message.length()];
		    for (int i = 0; i < message.length(); i++) {
		        String string = message;
		        data = string.getBytes(Charset.defaultCharset()); // you can chose charset
		    }
		    byte[] byteDataToEncrypt = data;*/
			
			byte[] byteDataToEncrypt = message.getBytes();
			System.out.println("byte" + byteDataToEncrypt.toString());
			/*byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt); 
	   		byte[] byteDecryptedText = desCipher.doFinal(byteCipherText);
	   		String strDecryptedText = new String(byteDecryptedText, "UTF-8");*/
	   		
	   		
	   		//System.out.println("Decrypted Text message is: " + strDecryptedText);
	   		//return strDecryptedText;
			return "c";
		}
		/*catch (NoSuchPaddingException e) {
        	e.printStackTrace();
        	return null;
        }*/
        catch (InvalidKeyException e) {
        	e.printStackTrace();
        	return null;
        }
		catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
        	return null;
		}
		/*catch (IllegalBlockSizeException e) {
        	e.printStackTrace();
        	return null;
        }
        catch(BadPaddingException e) {
        	e.printStackTrace();
        	return null;
        }
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
        	return null;
		}*/
	}
}
