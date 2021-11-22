package com.fih.ishareing.utils.signature;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

public class SignatureValidator {

	private final String MAC_NAME = "HmacSHA1";

	private String accessKeySecret;

	public SignatureValidator(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String encode(String xVersion, String xAccessKeyId, String xSignatureMethod, String xTimeStamp,
			String xSignatureVersion, String xSignatureNonce) {
		String[] array = new String[] { xVersion, xAccessKeyId, xSignatureMethod, xTimeStamp, xSignatureVersion,
				xSignatureNonce };
		String data = String.join("\n", array);

		if (this.accessKeySecret == null)
			throw new NullPointerException("secret nod found");

		try {
			byte[] decodedKey = accessKeySecret.getBytes();
			SecretKeySpec keySpec = new SecretKeySpec(decodedKey, MAC_NAME);
			Mac mac = Mac.getInstance(MAC_NAME);
			mac.init(keySpec);
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			byte[] signatureBytes = mac.doFinal(dataBytes);
			String signature = new String(Base64.getEncoder().encode(signatureBytes), StandardCharsets.UTF_8);
			return signature;
		} catch (Exception ex) {
			return null;
		}
	}

	public Boolean verify(String xVersion, String xAccessKeyId, String xSignatureMethod, String xTimeStamp,
			String xSignatureVersion, String xSignatureNonce, String xSignature) {

		if (StringUtils.isBlank(xSignature))
			return false;

		if (StringUtils.isBlank(xVersion) || StringUtils.isBlank(xAccessKeyId) || StringUtils.isBlank(xSignatureMethod)
				|| StringUtils.isBlank(xTimeStamp) || StringUtils.isBlank(xSignatureVersion)
				|| StringUtils.isBlank(xSignatureNonce))
			return false;
		try {

			String signature = encode(xVersion, xAccessKeyId, xSignatureMethod, xTimeStamp, xSignatureVersion,
					xSignatureNonce);

			if (xSignature.equals(signature)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}