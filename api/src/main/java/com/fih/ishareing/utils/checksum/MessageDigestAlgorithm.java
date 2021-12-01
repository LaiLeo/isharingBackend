package com.fih.ishareing.utils.checksum;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MessageDigestAlgorithm {

	public static String digestHex(String message) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hash);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String digestHex(String message, String algorithm) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hash);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte b : bytes)
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		return result.toString();
	}
}