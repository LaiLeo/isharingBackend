package com.fih.ishareing.utils.checksum;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5MessageDigest extends AbstractMessageDigest {

	@Override
	protected MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5");
	}

}
