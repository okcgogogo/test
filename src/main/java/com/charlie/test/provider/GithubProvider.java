package com.charlie.test.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 */
@Component
public class GithubProvider {

    public String getAccessToken(String json) {
        MediaType media = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        String url = "https://github.com/login/oauth/access_token";
        RequestBody body = RequestBody.create(media, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String out = str.split("&")[0].split("=")[1];
            return out;
        } catch (IOException e) {
        }
        return null;
    }


    public JSONObject getUser(String token) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String out = response.body().string();
            return JSON.parseObject(out);
        } catch (IOException e) {
        }
        return null;
    }
}

