package com.fih.ishareing.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FsnApiUtils {
    public final static String REDIS_KEY_ACCESS = "fsntoken:access";
    public final static String REDIS_KEY_REFRESH = "fsntoken:fresh";

//    @Value("${fusionnet.api.client}")
//    private String client;
//
//    @Value("${fusionnet.api.secret}")
//    private String secret;
//
//    @Value("${url.coreApi}")
//    private String urlCoreApiUrl;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String httpGet(String url, String payload, String token) {
        String resp = "";
        logger.info("url : " + url);
        logger.info("payload : " + payload);

        try {
            HttpClient httpClient = httpClient();
            HttpGet get = new HttpGet(url);

            Map<String, String> parameters = getHeadersForKong();
            get.addHeader("Authorization", parameters.get("Authorization"));
            get.addHeader("x-date", parameters.get("x-date"));
            get.addHeader("X-Access-Token", token);

            logger.info("Send process start");
            HttpResponse response = httpClient.execute(get);
            resp = EntityUtils.toString(response.getEntity());

            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
            logger.debug(resp);

            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());

            if (statusCode.equals("200")) {
                try {
                    if (resp.contains("Fail")) {
                        //return new Result<String>(CustErrorCode.FAIL, CustErrorCode.FAIL.getMsg(), resp);
                    }

                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }

            } else {
                logger.error("send statsus code : " + statusCode.toString());
                logger.error("send ErrorMsg : " + resp);
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return resp;
    }

    public String httpPost(String url, String payload, String token) {
        String resp = "";
        logger.info("url : " + url);
        logger.info("payload : " + payload);
        try {
            HttpClient httpClient = httpClient();
            HttpPost post = new HttpPost(url);

            Map<String, String> parameters = getHeadersForKong();
            post.addHeader("Authorization", parameters.get("Authorization"));
            post.addHeader("x-date", parameters.get("x-date"));
            post.addHeader("X-Access-Token", token);
            post.addHeader("Content-Type", "application/json; charset=utf-8");

            StringEntity entity = new StringEntity(payload, "UTF-8");
            post.setEntity(entity);

            logger.info("Send process start");
            HttpResponse response = httpClient.execute(post);
            resp = EntityUtils.toString(response.getEntity());

            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
            logger.debug(resp);

            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());

            if (statusCode.equals("200")) {
                try {
                    if (resp.contains("Fail")) {
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

        return resp;
    }


    public String httpPatch(String url, String payload, String token) {
        String resp = "";
        logger.info("url : " + url);
        logger.info("payload : " + payload);
        try {
            HttpClient httpClient = httpClient();
            HttpPatch patch = new HttpPatch(url);

            Map<String, String> parameters = getHeadersForKong();
            patch.addHeader("Authorization", parameters.get("Authorization"));
            patch.addHeader("x-date", parameters.get("x-date"));
            patch.addHeader("X-Access-Token", token);
            patch.addHeader("Content-Type", "application/json; charset=utf-8");

            StringEntity entity = new StringEntity(payload, "UTF-8");

            patch.setEntity(entity);

            logger.info("Send process start");
            HttpResponse response = httpClient.execute(patch);
            resp = EntityUtils.toString(response.getEntity());

            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
            logger.debug(resp);


            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());

            if (statusCode.equals("200")) {
                try {
                    if (resp.contains("Fail")) {
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

        return resp;
    }


    public String httpDelete(String url, String payload, String token) {
        String resp = "";
        logger.info("url : " + url);
        logger.info("payload : " + payload);

        try {
            HttpClient httpClient = httpClient();
            HttpDelete delete = new HttpDelete(url);

            Map<String, String> parameters = getHeadersForKong();
            delete.addHeader("Authorization", parameters.get("Authorization"));
            delete.addHeader("x-date", parameters.get("x-date"));
            delete.addHeader("X-Access-Token", token);

            logger.info("Send process start");
            HttpResponse response = httpClient.execute(delete);
            resp = EntityUtils.toString(response.getEntity());

            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
            logger.debug(resp);

            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());

            if (statusCode.equals("200")) {
                try {
                    if (resp.contains("Fail")) {
                    }

                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }

            } else {
                logger.error("send statsus code : " + statusCode.toString());
                logger.error("send ErrorMsg : " + resp);
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return resp;
    }

    private Map<String, String> getHeadersForKong() {
        Map<String, String> parameters = new HashMap<String, String>();
        Date date = new Date();

//        String pattern = "E, dd MMM yyyy HH:mm:ss z";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//
//        String dateString = simpleDateFormat.format(date);
//        String signingString = "x-date: " + dateString;
//        String signature = "";
//        try {
//            byte[] decodedKey = secret.getBytes();
//            SecretKeySpec keySpec = new SecretKeySpec(decodedKey, "HmacSHA1");
//            Mac mac = Mac.getInstance("HmacSHA1");
//            mac.init(keySpec);
//            byte[] dataBytes = signingString.getBytes(StandardCharsets.UTF_8);
//            byte[] signatureBytes = mac.doFinal(dataBytes);
//            signature = new String(Base64.getEncoder().encode(signatureBytes), StandardCharsets.UTF_8);
//
//            signature = "hmac username=\"" + client + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"" + signature + "\"";
//        } catch (Exception ex) {
//            return null;
//        }
//
//        parameters.put("Authorization", signature);
//        parameters.put("x-date", dateString);

        return parameters;
    }

    public String getValidToken(String appCode) {
        String token = "";
        String refreshToken = "";

        // get token by redis
        token = redisTemplate.opsForValue().get(keyAccessToken(appCode));
        if (token == null || token.equals("")) {
            // get token by coreApi
//            Application application = appRep.findByCodeAndActive(appCode, true);
//            if (application != null) {
//                token = getAccessToken(application);
//            }
        }

        return token;
    }

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

//    private String getAccessToken(Application application) {
//        String tokenUrl = urlCoreApiUrl + "auth/api/v3/auth/token";
//        JSONObject responseJson = new JSONObject();
//        String resp = "";
//        String token = "";
//        logger.info("tokenUrl : " + tokenUrl);
//
//        try {
//            HttpClient httpClient = httpClient();
//            Map<String, String> parameters = getHeadersForKong();
//            HttpPost post = new HttpPost(tokenUrl);
//
//
//            post.addHeader("Content-Type", "application/json; charset=utf-8");
//            post.addHeader("Authorization", parameters.get("Authorization"));
//            post.addHeader("x-date", parameters.get("x-date"));
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("account", application.getAccount());
//            jsonObject.put("password", new String(Base64.getDecoder().decode(application.getPassword()), "UTF-8"));
//
//            StringEntity entity = new StringEntity(jsonObject.toJSONString(), "UTF-8");
//            post.setEntity(entity);
//
//            logger.info("Send process start");
//            HttpResponse response = httpClient.execute(post);
//            resp = EntityUtils.toString(response.getEntity());
//
//            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
//            logger.debug(resp);
//
//            responseJson = JSONObject.parseObject(resp);
//
//
//            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
//
//            if (statusCode.equals("200")) {
//                try {
//                    if (!resp.contains("Fail")) {
//                        redisTemplate.opsForValue().set(keyAccessToken(application.getCode()), responseJson.getString("accessToken"), 7200, TimeUnit.SECONDS);
////                        redisTemplate.opsForValue().set(keyRefreshToken(application.getCode()), responseJson.getString("refreshToken"));
//                        token = responseJson.getString("accessToken");
//                    }
//
//
//                } catch (Exception ex) {
//                    logger.error(ex.getMessage());
//                }
//
//            } else {
//                logger.error("send statsus code : " + statusCode.toString());
//                logger.error("send ErrorMsg : " + resp);
//            }
//
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//        }
//
//
//        return token;
//    }


    private String getAccessTokenByRefreshToken(String refreshToken, String appCode) {
//        String tokenUrl = urlCoreApiUrl + "auth/api/v3/auth/token";
        JSONObject responseJson = new JSONObject();
        String resp = "";
        String token = "";
//        logger.info("tokenUrl : " + tokenUrl);
//
//        try {
//            HttpClient httpClient = httpClient();
//            Map<String, String> parameters = getHeadersForKong();
//            HttpPut put = new HttpPut(tokenUrl);
//
//
//            put.addHeader("Content-Type", "application/json; charset=utf-8");
//            put.addHeader("Authorization", parameters.get("Authorization"));
//            put.addHeader("x-date", parameters.get("x-date"));
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("refreshToken", refreshToken);
//
//            StringEntity entity = new StringEntity(jsonObject.toJSONString(), "UTF-8");
//            put.setEntity(entity);
//
//            logger.info("Send process start");
//            HttpResponse response = httpClient.execute(put);
//            resp = EntityUtils.toString(response.getEntity());
//
//            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
//            logger.debug(resp);
//
//            responseJson = JSONObject.parseObject(resp);
//
//
//            String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
//
//            if (statusCode.equals("200")) {
//                try {
//                    if (!resp.contains("Fail")) {
//                        redisTemplate.opsForValue().set(keyAccessToken(appCode), responseJson.getString("accessToken"), 7200, TimeUnit.SECONDS);
////                        redisTemplate.opsForValue().set(keyRefreshToken(appCode), responseJson.getString("refreshToken"));
//                        token = responseJson.getString("accessToken");
//                    }
//
//
//                } catch (Exception ex) {
//                    logger.error(ex.getMessage());
//                }
//
//            } else {
//                logger.error("send statsus code : " + statusCode.toString());
//                logger.error("send ErrorMsg : " + resp);
//            }
//
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//        }


        return token;
    }

    private static String keyAccessToken(String appCode) {
        return String.format("%s:%s", REDIS_KEY_ACCESS, appCode);
    }

    private static String keyRefreshToken(String appCode) {
        return String.format("%s:%s", REDIS_KEY_REFRESH, appCode);
    }
}
