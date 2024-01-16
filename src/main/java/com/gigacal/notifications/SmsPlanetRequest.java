package com.gigacal.notifications;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmsPlanetRequest {

    private static final boolean LOCALHOST_TEST = false;
    private static final String BASE_URL = (LOCALHOST_TEST) ? "http://localhost:8080" : "https://api2.smsplanet.pl";
    private final static Charset UTF_8_CHARSET = StandardCharsets.UTF_8;

    final String path;
    final boolean get;
    final HashMap<String, String> parameters = new HashMap<>();

    public SmsPlanetRequest(String path) {
        this.path = path;
        this.get = false;
    }

    public URI getURI() {
        try {
            URIBuilder builder = new URIBuilder(BASE_URL);
            builder.setPath(LOCALHOST_TEST ? "/p/api2/" + path : path);
            if (this.get) {
                for (Map.Entry<String, String> entry : parameters.entrySet())
                    builder.setParameter(entry.getKey(), entry.getValue());
            }
            return builder.build();
        } catch (URISyntaxException e) {
            // This should never happen, all urls are defined in this file.
            return null;
        }
    }

    public SmsPlanetResponse execute() throws IOException {
        HttpPost http = new HttpPost(getURI());

        List<NameValuePair> parametersAsList = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet())
            parametersAsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        http.setEntity(new UrlEncodedFormEntity(parametersAsList, UTF_8_CHARSET));

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(http)){
            return new SmsPlanetResponse(EntityUtils.toString(response.getEntity()));
        }
    }

    @Override
    public String toString() {
        return getURI().toString();
    }

    public static SmsPlanetRequest sendSMS(String key, String password, String from, String msg, String to) {
        SmsPlanetRequest request = new SmsPlanetRequest("sms");
        request.parameters.put("key", key);
        request.parameters.put("password", password);
        request.parameters.put("from", from);
        request.parameters.put("msg", msg);
        request.parameters.put("to", to);
        return request;
    }

}