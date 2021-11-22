package com.fih.ishareing.configurations.interceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fih.ishareing.controller.base.error.ApiErrorResponse;
import com.fih.ishareing.errorHandling.ErrorHandlingConstants;
import com.fih.ishareing.utils.date.DateUtils;
import com.fih.ishareing.utils.json.JacksonJsonConverter;
import com.fih.ishareing.utils.json.JsonConverter;
import com.fih.ishareing.utils.signature.RequestHeaderUtils;
import com.fih.ishareing.utils.signature.ApiSignature;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ApiSignatureInterceptor extends HandlerInterceptorAdapter {

    private static int LIMIT_TIME_MINUTES = 10;
    private static long LIMIT_TIME_MILLIS = TimeUnit.MINUTES.toMillis(LIMIT_TIME_MINUTES);

    private static final Logger logger = LoggerFactory.getLogger(ApiSignatureInterceptor.class);

    private ApiSignature apiSignature = new ApiSignature();
    private JsonConverter jsonConverter = JacksonJsonConverter.create();
    private Cache<String, Boolean> cache;

    public ApiSignatureInterceptor(ApiSignature apiSignature) {
        this.apiSignature = apiSignature;

        cache = CacheBuilder.newBuilder().expireAfterWrite(LIMIT_TIME_MINUTES, TimeUnit.MINUTES).build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {

            Map<String, String> signatureHeaders = RequestHeaderUtils.getSignatureHeaders(request);
            if (signatureHeaders.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ApiErrorResponse error = new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.invalidSignature);
                response.getWriter().write(jsonConverter.serialize(error));
                return false;
            }

            // check timestamp
            Long timestamp = Long.valueOf(signatureHeaders.get("Timestamp"));
            if (DateUtils.UTCtimeNotIn(timestamp, LIMIT_TIME_MILLIS)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ApiErrorResponse error = new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.invalidSignature.name(),
                        "Timestamp is invalid");
                response.getWriter().write(jsonConverter.serialize(error));

                return false;
            }

            String nonce = signatureHeaders.get("SignatureNonce");
            if (StringUtils.isBlank(nonce) || cache.asMap().containsKey(nonce)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ApiErrorResponse error = new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.invalidSignature.name(),
                        "SignatureNonce is invalid");
                response.getWriter().write(jsonConverter.serialize(error));
                return false;
            } else {
                cache.put(nonce, true);
            }

            Boolean signatureResult = verifySignature(signatureHeaders);
            if (!signatureResult) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ApiErrorResponse error = new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.invalidSignature);
                response.getWriter().write(jsonConverter.serialize(error));
                return false;
            }

            return super.preHandle(request, response, handler);
        } catch (NumberFormatException e) {
            logger.error("Timestamp is invalid");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ApiErrorResponse error = new ApiErrorResponse(ErrorHandlingConstants.ERROR_BUNDLE.invalidSignature.name(), "Timestamp is invalid");
            response.getWriter().write(JacksonJsonConverter.create().serialize(error));

            return false;
        }
    }

    private Boolean verifySignature(Map<String, String> signatureHeaders) {
        try {
            return apiSignature.verify(signatureHeaders.get("Version"), signatureHeaders.get("AccessKeyId"),
                    signatureHeaders.get("SignatureMethod"), signatureHeaders.get("Timestamp"),
                    signatureHeaders.get("SignatureVersion"), signatureHeaders.get("SignatureNonce"),
                    signatureHeaders.get("Signature"));
        } catch (Exception e) {
            return false;
        }
    }

}