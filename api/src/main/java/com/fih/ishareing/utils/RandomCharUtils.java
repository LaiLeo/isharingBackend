package com.fih.ishareing.utils;

import java.security.SecureRandom;
import org.springframework.stereotype.Service;

@Service
public class RandomCharUtils  {

	static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	static SecureRandom rnd = new SecureRandom();

	public String randomString(int len){
		   StringBuilder sb = new StringBuilder(len);
		   for(int i = 0; i < len; i++)
		      sb.append(AB.charAt(rnd.nextInt(AB.length())));
		   return sb.toString();
	}
	
}
