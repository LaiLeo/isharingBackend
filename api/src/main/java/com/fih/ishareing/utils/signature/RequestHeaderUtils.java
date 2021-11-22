package com.fih.ishareing.utils.signature;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequestHeaderUtils {

	// Request headers
	private static final String REQUEST_X_REQUEST_ID = "X-Request-ID";
	private static final String REQUEST_METHOD = "Method";
	private static final String REQUEST_USER_AGENT = "User-Agent";
	private static final String REQUEST_ACCEPT_LANGUAGE = "Accept-Language";
	private static final String REQUEST_URL = "Url";

	// Signature headers
	public static final String SIGNATURE_VERSION = "Version";
	public static final String SIGNATURE_ACCESS_KEY_ID = "AccessKeyId";
	public static final String SIGNATURE_TIMESTAMP = "Timestamp";;
	public static final String SIGNATURE_METHOD = "SignatureMethod";;
	public static final String SIGNATURE_SIGNATURE_VERSION = "SignatureVersion";;
	public static final String SIGNATURE_SIGNATURE_NONCE = "SignatureNonce";
	public static final String SIGNATURE_VALUE = "Signature";

	private static final List<String> SIGNATURE_HEADERS = Arrays.asList(SIGNATURE_VERSION, SIGNATURE_ACCESS_KEY_ID,
			SIGNATURE_TIMESTAMP, SIGNATURE_METHOD, SIGNATURE_SIGNATURE_VERSION, SIGNATURE_SIGNATURE_NONCE,
			SIGNATURE_VALUE);

	public static Map<String, String> getRequestHeaders(HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put(REQUEST_X_REQUEST_ID, getXRequestID(request));
		parameters.put(REQUEST_METHOD, request.getMethod());
		parameters.put(REQUEST_USER_AGENT, request.getHeader(REQUEST_USER_AGENT));

		String acceptLanguage = request.getHeader(REQUEST_ACCEPT_LANGUAGE);
		if (StringUtils.isNotBlank(acceptLanguage))
			parameters.put(REQUEST_ACCEPT_LANGUAGE, acceptLanguage);
		else
			parameters.put(REQUEST_ACCEPT_LANGUAGE, "en-US");

		String url = request.getRequestURI();
		if (StringUtils.isNotBlank(url)) {
			if (StringUtils.isBlank(request.getQueryString()))
				parameters.put(REQUEST_URL, url);
			else
				parameters.put(REQUEST_URL, String.format("%s?%s", url, request.getQueryString()));
		}

		return parameters;
	}

	public static Map<String, String> getSignatureHeaders(HttpServletRequest request) {

		Map<String, String> parameters = new HashMap<String, String>();
		SIGNATURE_HEADERS.forEach(key -> {
			String value = request.getHeader(key);
			if (StringUtils.isNotBlank(value))
				parameters.put(key, value);
		});

		return parameters;
	}

	private static String getXRequestID(HttpServletRequest requestWrapper) {
		String xRequestID = requestWrapper.getHeader(REQUEST_X_REQUEST_ID);

		if (StringUtils.isBlank(xRequestID)) {
			xRequestID = UUID.randomUUID().toString();
		} else {
		}

		return xRequestID;
	}
}
