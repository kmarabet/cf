package com.cf.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class ConnectionUtils {

    public static JsonArray requestJsonArrayByUrl(final String url) throws IOException {
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.connect();
        //Reader reader = new InputStreamReader(PullingDataController.class.getResourceAsStream("/Server2.json"), "UTF-8"))
        JsonReader reader = new JsonReader(
                new InputStreamReader(urlConnection.getInputStream()));
        JsonParser parser = new JsonParser();
        JsonElement rootElement = parser.parse(reader);
        return rootElement.getAsJsonArray();
    }
}
