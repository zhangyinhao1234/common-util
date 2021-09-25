package org.yinhao.common.util.net;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class HttpClientHelper {

    private static final String APPLICATION_X_WWW_FORM_URLENCODED = ContentType.APPLICATION_FORM_URLENCODED
            .getMimeType();
    private static final String APPLICATION_JSON = ContentType.APPLICATION_JSON.getMimeType();

    private static final String CHARTSET = "UTF-8";

    private static final int CONNTIMEOUT = 60000;

    private static final int READTIMEOUT = 60000;

    private static CloseableHttpClient httpClient = null;

    static {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();

        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf).register("https", sslsf).build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);

        // 将最大连接数增加到1000
        cm.setMaxTotal(50);

        // 将每个路由基础的连接增加到100
        cm.setDefaultMaxPerRoute(10);

        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }


    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        StringBuffer sb = new StringBuffer(url);
        if (params != null & !params.isEmpty()) {
            sb.append("?");
            for (Map.Entry<String, String> set : params.entrySet()) {
                sb.append(set.getKey()).append("=").append(set.getValue()).append("&");
            }
        }
        return get(sb.toString(), headers);
    }

    public static String get(String url, Map<String, String> headers) throws IOException {
        return get(url, CHARTSET, CONNTIMEOUT, READTIMEOUT, headers);
    }

    public static String get(String url, String charset, Integer connTimeout, Integer readTimeout, Map<String, String> headers)
            throws UnsupportedOperationException, IOException {
        HttpGet get = new HttpGet(url);
        if (headers != null) {
            for (Map.Entry<String, String> enriry : headers.entrySet()) {
                get.setHeader(enriry.getKey(), enriry.getValue());
            }
        }
        try {
            RequestConfig customReqConfig = getCustomReqConfig(connTimeout, readTimeout);
            get.setConfig(customReqConfig);
            HttpResponse res = httpClient.execute(get);
            return EntityUtils.toString(res.getEntity(), charset);
        } finally {
            get.releaseConnection();
        }
    }

    private static RequestConfig getCustomReqConfig(Integer connTimeout, Integer readTimeout) {
        RequestConfig.Builder customReqConf = RequestConfig.custom();
        if (connTimeout != null) {
            customReqConf.setConnectTimeout(connTimeout);
        }
        if (readTimeout != null) {
            customReqConf.setSocketTimeout(readTimeout);
        }
        return customReqConf.build();
    }
}
