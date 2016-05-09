package com.springapp.mvc.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivek on 7/5/16.
 */
public class FacebookCallbackPostMessage {
    private JsonObject jsonObject;
    private JsonArray messagingEvents;

    public FacebookCallbackPostMessage(String message) {
        JsonObject jsonObject = new Gson().fromJson(message, JsonObject.class);
        messagingEvents = jsonObject.getAsJsonArray("entry").get(0).getAsJsonObject().getAsJsonArray("messaging");
    }

    public List<String> getAllSenders() {
        List<String> senders = new ArrayList<String>();
        for (int i=0;i<messagingEvents.size();i++) {
            JsonObject event = messagingEvents.get(i).getAsJsonObject();
            senders.add(event.getAsJsonObject("sender").get("id").getAsString());
        }
        return senders;
    }

    public List<String> getAllMessages() {
        List<String> messages = new ArrayList<String>();
        for (int i=0;i<messagingEvents.size();i++) {
            JsonObject event = messagingEvents.get(i).getAsJsonObject();
            if (event.getAsJsonObject("message") != null && event.getAsJsonObject("message").get("text").getAsString() != null) {
                messages.add(event.getAsJsonObject("message").get("text").getAsString());
            }
        }
        return messages;
    }
}
