package com.hangover;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by leechunhoe on 7/11/15.
 */
public class Util
{
    /**
     * Send HTTP request
     * @param url Url
     * @param requestMethod GET, POST, PUT, DELETE
     * @param body Body for POST, PUT, DELETE
     * @return { (int) status code, (String) result }
     */
    public static Object[] sendHttpRequest(String url, String requestMethod, Serializable body)
    {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        if (body != null)
        {
            String bodyJson = (new Gson()).toJson(body);
            RequestBody requestBody = RequestBody.create(JSON, bodyJson);
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url);

            if (requestMethod == null)
            {
                return null;
            }

            if (requestMethod.equals(Common.REQUEST_METHOD_GET))
            {
                requestBuilder.get();
            }
            else if (requestMethod.equals(Common.REQUEST_METHOD_POST))
            {
                requestBuilder.post(requestBody);
            }
            else if (requestMethod.equals(Common.REQUEST_METHOD_PUT))
            {
                requestBuilder.put(requestBody);
            }
            else if (requestMethod.equals(Common.REQUEST_METHOD_DELETE))
            {
                requestBuilder.delete(requestBody);
            }
            else
            {
                return null;
            }

            Request request = requestBuilder.build();

            try
            {
                Response response = client.newCall(request).execute();

                return new Object[]{ response.code(), response.body().string() };

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }
}
