package com.fih.ishareing.utils.checksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Md5Checksum {

	public static String digestHex(String filePath) throws IOException {

		File file = new File(filePath);
		if (!file.exists()) {
			throw new IOException("The file is not exist.");
		}

		return digestHex(file);
	}

	public static String digestHex(File file) throws IOException {

		if (!file.exists()) {
			throw new IOException("The file is not exist.");
		}

		FileInputStream fis = null;
		DigestInputStream dis = null;
		byte[] buff = new byte[1024];
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			dis = new DigestInputStream(fis, md);
			// Read bytes from the file.
			while (dis.read(buff) != -1)
				;

			byte[] md5Digests = md.digest();
			return bytesToHex(md5Digests);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			buff = null;
			if (fis != null)
				fis.close();
			if (dis != null)
				dis.close();
		}
		return null;
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte b : bytes)
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		return result.toString();
	}
}
