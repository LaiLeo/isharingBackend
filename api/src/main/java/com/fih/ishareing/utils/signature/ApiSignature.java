package com.fih.ishareing.utils.signature;

import java.util.HashMap;
import java.util.Map;

public class ApiSignature {
	private Map<String, String> clientList = new HashMap<String, String>();

	public ApiSignature() {
	}

	public String encode(String xVersion, String xAccessKeyId, String xSignatureMethod, String xTimeStamp,
			String xSignatureVersion, String xSignatureNonce) {

		String accessKeySecret = this.clientList.get(xAccessKeyId);
		if (accessKeySecret == null)
			throw new NullPointerException("secret nod found");

		SignatureValidator validator = new SignatureValidator(accessKeySecret);
		return validator.encode(xVersion, xAccessKeyId, xSignatureMethod, xTimeStamp, xSignatureVersion,
				xSignatureNonce);
	}

	public Boolean verify(String xVersion, String xAccessKeyId, String xSignatureMethod, String xTimeStamp,
			String xSignatureVersion, String xSignatureNonce, String xSignature) {
		String accessKeySecret = this.clientList.get(xAccessKeyId);
		if (accessKeySecret == null)
			throw new NullPointerException("secret nod found");

		SignatureValidator validator = new SignatureValidator(accessKeySecret);
		return validator.verify(xVersion, xAccessKeyId, xSignatureMethod, xTimeStamp, xSignatureVersion,
				xSignatureNonce, xSignature);
	}

	public void setClients(Map<String, String> clients) {
		this.clientList = clients;
	}
}
