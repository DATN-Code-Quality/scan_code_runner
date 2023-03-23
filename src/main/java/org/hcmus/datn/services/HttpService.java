package org.hcmus.datn.services;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class HttpService {
    private static final OkHttpClient client = new OkHttpClient();

    public static Request newGetRequest(String url, Map<String, String> queryParams, Map<String, String> headers) {
        Request.Builder requestBuiler = newRequestBuilder(url, queryParams, headers);
        requestBuiler = requestBuiler.get();

        return requestBuiler.build();
    }

    public static Request newPostRequest(String url, RequestBody body,Map<String, String> queryParams, Map<String, String> headers)
    {
        Request.Builder requestBuiler = newRequestBuilder(url, queryParams, headers);
        requestBuiler.post(body);

        return requestBuiler.build();
    }

    private static Request.Builder newRequestBuilder(String url, Map<String, String> queryParams, Map<String, String> headers) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for (String param : queryParams.keySet()) {
            urlBuilder.addQueryParameter(param, queryParams.get(param));
        }
        String requestURL = urlBuilder.toString();

        Request.Builder requestBuiler = new Request.Builder().url(requestURL);
        for (String header : headers.keySet()) {
            requestBuiler = requestBuiler.addHeader(header, headers.get(header));
        }

        return requestBuiler;
    }


    public static Response excuteRequest(Request request) {
        try {
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
