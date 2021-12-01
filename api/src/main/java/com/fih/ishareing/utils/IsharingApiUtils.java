package com.fih.ishareing.utils;

import com.alibaba.fastjson.JSONObject;
import com.fih.ishareing.errorHandling.exceptions.UserNotFoundException;
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
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IsharingApiUtils {
    @Value("${isharingUrl}")
    private String isharingUrl;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Bean
    public CloseableHttpClient httpIsharingClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
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

    public String getAccessToken(Integer userId) {
        String accessToken = null;
        String resp = "";
        logger.info("url:{},username:{}", isharingUrl, userId);

        try {
            HttpClient httpClient = httpIsharingClient();
            HttpPost post = new HttpPost(isharingUrl);

            post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", userId.toString()));
            params.add(new BasicNameValuePair("apikey", "fa470c61e1f01f0d13ffb9b3b7008e9c"));
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

            logger.info("Send process start");
            HttpResponse response = httpClient.execute(post);
            resp = EntityUtils.toString(response.getEntity());

            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
            logger.debug(resp);

            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
            if (statusCode.equals("200")) {
                try {
                    JSONObject resJson = JSONObject.parseObject(resp);
                    logger.info("{}", resJson.toJSONString());
                    accessToken = resJson.getString("token");

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


        return accessToken;
    }
}
