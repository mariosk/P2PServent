package saicontella.core;

/**
 * SaiconTella project
 *
 * @author Marios Karagiannopoulos (mariosk@gmail.com)
 *
 * February 2008
 */

import java.io.*; 
import java.security.*;

public class STDigestFileHash {

	private MessageDigest currentAlgorithm; 
	public final String SHA1_DIGEST = "SHA-1";
	public final String MD5_DIGEST = "MD5";
	
	public String getDigest(String Digest, String filename) {
		StringBuffer digestvalues = new StringBuffer() ;
		try { 
			currentAlgorithm = MessageDigest.getInstance(Digest); 
			digestvalues.append(computeDigest(loadBytes(filename)));
			return digestvalues.toString().trim(); 
		} 
		catch(NoSuchAlgorithmException e) { 
			digestvalues.append(Digest + " algorithm not available."); 
		}		
		return null;
	}
	
	private String computeDigest(byte[] b) { 
		currentAlgorithm.reset(); 
		currentAlgorithm.update(b); 
		byte[] hash = currentAlgorithm.digest(); 
		String d = " "; 
		int usbyte = 0; // unsigned byte 
		for (int i = 0; i < hash.length; i+=2) { // format with 2-byte words with spaces. 
			usbyte = hash[i] & 0xFF ; // byte-wise AND converts signed byte to unsigned. 
			if(usbyte<16) 
				d += "0" + Integer.toHexString(usbyte); // pad on left if single hex digit. 
			else 
				d += Integer.toHexString(usbyte); 
			usbyte = hash[i+1] & 0xFF ; // byte-wise AND converts signed byte to unsigned. 
			if(usbyte<16) 
				d += "0" + Integer.toHexString(usbyte) + " "; // pad on left if single hex digit. 
			else 
				d += Integer.toHexString(usbyte) + " "; 
		} 
		return d.toUpperCase(); 
	}	
	
	private byte[] loadBytes(String name) { 
		FileInputStream in = null; 
		try { 
			in = new FileInputStream(name); 
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			
			int bytesread = 0; 
			byte [] tbuff = new byte[512] ; 
			
			while(true) { 
				bytesread = in.read(tbuff); 
				if (bytesread == -1) // if EOF break ;
					break;
				buffer.write(tbuff,0,bytesread) ;				
			}
			return buffer.toByteArray();
		} 
		catch (IOException e) { 
			if (in != null) { 
				try { 
					in.close(); 
				} 
				catch (IOException e2) {} 
			} 
			return null; 
		} 
	} 		
		
}
