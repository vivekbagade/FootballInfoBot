package com.springapp.mvc.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.springapp.mvc.model.OutboundRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by vivek on 7/5/16.
 */
@Service
public class FacebookMessengerService {
    @Autowired private CurlingService curlingService;
    private static final String ACCESS_TOKEN = "EAADcOEnm1NcBALjo9AOalwT5CpibASIvGC6hWp8OsQuEdvCZAHCALprYc6C7Ya53A8RrJg2h5ZBlQZAa4Dx4SZCcunRkcTuo5E9eSCT5n94x5RRLMibABFQdL2z3ZAeieYmO0YlnRZCSXIFNe3OaV99X7EnRxZAIAkkl0QiYwjPSAZDZD";
    public void sendMessage(String recipient, String message) {
        JsonObject recipientObject = new JsonObject();
        recipientObject.addProperty("id", Long.parseLong(recipient));
        JsonObject messageObject = new JsonObject();
        messageObject.addProperty("text", message);
        JsonObject requestBody = new JsonObject();
        requestBody.add("recipient", recipientObject);
        requestBody.add("message", messageObject);
        System.out.println(new Gson().toJson(requestBody));

        OutboundRequest outboundRequest = new OutboundRequest();
        outboundRequest.setHost("graph.facebook.com");
        outboundRequest.setPath("v2.6/me/messages");
        outboundRequest.addParameter("access_token", ACCESS_TOKEN);
        outboundRequest.setRequestMethod("POST");
        outboundRequest.setProtocol(OutboundRequest.Protocol.HTTPS);
        outboundRequest.addHeader("Content-Type", "application/json");
        outboundRequest.setRequestBody(new Gson().toJson(requestBody));

        try {
            System.out.println(curlingService.makeCurlCall(outboundRequest));
        } catch (IOException e) {
            System.out.println("Curl failed with exception :");
            e.printStackTrace();
        }
    }
}
