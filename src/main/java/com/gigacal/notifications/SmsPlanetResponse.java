package com.gigacal.notifications;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class SmsPlanetResponse {

    private static final Gson gson = new Gson();

    private final String body;

    public boolean succeeded;
    public HashMap<String, Object> message;

    public SmsPlanetResponse(String body) {
        this.body = body;
        try {
            double d = Double.parseDouble(body);
            message = new HashMap<>();
            message.put("number", d);
        } catch (NumberFormatException nfe) {
            // Do nothing.
        }
        try {
            Type mapType = new TypeToken<HashMap<String, Object>>() {}.getType();
            message = gson.fromJson(body, mapType);
        } catch (com.google.gson.JsonSyntaxException ex) {
            // Do nothing.
        }
        succeeded = message != null;
    }

    public String getMessageId() {
        return succeeded ? (String) message.getOrDefault("messageId", "0") : null;
    }

    public int getErrorCode() {
        if (!succeeded)
            return -1;
        if (message.containsKey("errorMsg"))
            return ((Double) message.getOrDefault("errorCode", (double) 199)).intValue();
        return 0;
    }

    public String getErrorMessage() {
        return succeeded ? (String) message.getOrDefault("errorMsg",null) : null;
    }

    @Override
    public String toString() {
        return body;
    }
}