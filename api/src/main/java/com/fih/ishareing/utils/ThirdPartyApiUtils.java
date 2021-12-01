package com.fih.ishareing.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.URIParameter;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdPartyApiUtils {
    @Value("${fubonUrl}")
    private String fubonUrl;
    @Value("${twmUrl}")
    private String twmUrl;
    @Value("${twmRedirectUrl}")
    private String twmRedirectUrl;
    @Value("${twmClientId}")
    private String twmClientId;
    @Value("${twmAuthorization}")
    private String twmAuthorization;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Bean
    public CloseableHttpClient httpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
            RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
            .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
            .build());

        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(sslContext)
            .setConnectionManager(cm)
            .build();

        return httpClient;
    }

    public Boolean validFubonUser(String username, String passwordMD5) {
        Boolean validResult = false;
        String resp = "";
        logger.info("url:{},username:{},pass:{}", fubonUrl, username, passwordMD5);

        try {
            HttpClient httpClient = httpClient();
            HttpPost post = new HttpPost(fubonUrl);

            post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("fubon_account", "1"));
            params.add(new BasicNameValuePair("account", username));
            params.add(new BasicNameValuePair("code", passwordMD5));
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            logger.info("Send process start");
            HttpResponse response = httpClient.execute(post);
            resp = EntityUtils.toString(response.getEntity());

            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
            logger.debug(resp);

            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
            JSONObject resJson = JSONObject.parseObject(resp);
            logger.info("{}", resJson.toJSONString());
            Boolean isMember = StringUtils.stringToBoolean(resJson.getString("is_member"));
            if (statusCode.equals("200")) {
                try {
                    if (isMember) {
                        validResult = true;
                    }

                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            } else {
                logger.error("send statsus code : " + statusCode.toString());
                logger.error("send ErrorMsg : " + resp);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }


        return validResult;
    }

    public JSONObject getTwmUserProfile(String code) {
        JSONObject result = new JSONObject();
        String resp = "";
        String accessToken = null;
        logger.info("url:{},code:{}", twmUrl, code);

        try {
            HttpClient httpClient = httpClient();
            String getAccessTokenUrl = twmUrl + "/getAccessToken?";
            HttpPost post = new HttpPost(getAccessTokenUrl);

            post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            post.addHeader("Authorization","Basic " + twmAuthorization);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            String urlParameters = String.format("grant_type=%s&code=%s&redirect_uri=%s", "authorization_code", code, twmRedirectUrl);
            getAccessTokenUrl += urlParameters;
            post.setURI(URI.create(getAccessTokenUrl));
            logger.info(post.getURI().toString());
            logger.info("Send process start");
            HttpResponse responseAccessToken = httpClient.execute(post);
            resp = EntityUtils.toString(responseAccessToken.getEntity());

            logger.info(String.valueOf(responseAccessToken.getStatusLine().getStatusCode()));
            logger.debug(resp);

            String statusCode = String.valueOf(responseAccessToken.getStatusLine().getStatusCode());
            JSONObject resJson = JSONObject.parseObject(resp);
            logger.info("{}", resJson.toJSONString());
            if (statusCode.equals("200")) {
                try {
                    accessToken = resJson.getString("access_token");

                    if (accessToken != null) {
                        String getUserProfileUrl = twmUrl + "/getUserProfile";
                        post = new HttpPost(getUserProfileUrl);

                        post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
                        post.addHeader("Authorization","Bearer " + accessToken);

                        logger.info("Send process start");
                        responseAccessToken = httpClient.execute(post);
                        resp = EntityUtils.toString(responseAccessToken.getEntity());

                        logger.info(String.valueOf(responseAccessToken.getStatusLine().getStatusCode()));
                        logger.info(resp);

                        statusCode = String.valueOf(responseAccessToken.getStatusLine().getStatusCode());
                        resJson = JSONObject.parseObject(resp);
                        logger.info("{}", resJson.toJSONString());

                        if (statusCode.equals("200")) {
                            try {
                                result = resJson;

                            } catch (Exception ex) {
                                logger.error(ex.getMessage());
                            }
                        } else {
                            logger.error("send statsus code : " + statusCode.toString());
                            logger.error("send ErrorMsg : " + resp);
                        }
                    }

                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            } else {
                logger.error("send statsus code : " + statusCode.toString());
                logger.error("send ErrorMsg : " + resp);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }


        return result;
    }
}
