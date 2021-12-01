package com.fih.ishareing.utils.checksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class AbstractMessageDigest {
	public String hash(File file) throws IOException {
		FileInputStream inputStream = null;
		FileChannel channel = null;
		ByteBuffer buffer = null;
		
		try {
			MessageDigest digest = getMessageDigest();
			inputStream = new FileInputStream(file);
		    channel = inputStream.getChannel();
	
			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				// you could make this work with some care,
				// but this code does not bother.
				throw new IOException("File " + file.getAbsolutePath() + " is too large.");
			}
			
		    buffer = channel.map(MapMode.READ_ONLY, 0, length);
			int bufsize = 1024 * 8;
			byte[] temp = new byte[bufsize];
			int bytesRead = 0;

			while (bytesRead < length) {
				int numBytes = (int) length - bytesRead >= bufsize ? bufsize : (int) length - bytesRead;
				buffer.get(temp, 0, numBytes);
				digest.update(temp, 0, numBytes);
				bytesRead += numBytes;
			}
			
			byte[] mdbytes = digest.digest();
		
			return bytesToHex(mdbytes);
			
		
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unsupported Hash Algorithm.", e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Unsupported Hash Algorithm.", e);
		} finally {
			if (channel != null) { channel.close(); }			
			if (inputStream != null) { inputStream.close();}
				
			closeDirectBuffer(buffer);
			
		}
	}
		
	
	private void closeDirectBuffer(ByteBuffer cb) {
	    if (cb==null || !cb.isDirect()) return;
	
	    // we could use this type cast and call functions without reflection code,
	    // but static import from sun.* package is risky for non-SUN virtual machine.
	    //try { ((sun.nio.ch.DirectBuffer)cb).cleaner().clean(); } catch (Exception ex) { }
	    try {
	        Method cleaner = cb.getClass().getMethod("cleaner");
	        cleaner.setAccessible(true);
	        Method clean = Class.forName("sun.misc.Cleaner").getMethod("clean");
	        clean.setAccessible(true);
	        clean.invoke(cleaner.invoke(cb));
	    } catch(Exception ex) { }
	    cb = null;
	}


	public String hash(String text) {
		try {
			MessageDigest digest = getMessageDigest();
			byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hash);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String digestHex(String message, String algorithm) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hash);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte b : bytes)
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		
		return result.toString();
	}

	protected abstract MessageDigest getMessageDigest() throws NoSuchAlgorithmException;
}
