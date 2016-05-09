package com.springapp.mvc.services;

import com.springapp.mvc.model.FacebookCallbackPostMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vivek on 7/5/16.
 */
@Service
public class EchoingService {
    @Autowired private FacebookMessengerService facebookMessengerService;
    public void echoMessages(String callbackPostBody) {
        FacebookCallbackPostMessage facebookCallbackPostMessage = new FacebookCallbackPostMessage(callbackPostBody);
        List<String> senders = facebookCallbackPostMessage.getAllSenders();
        List<String> messages = facebookCallbackPostMessage.getAllMessages();
        for (int i=0;i<senders.size();i++) {
            facebookMessengerService.sendMessage(senders.get(i), messages.get(i));
        }
    }
}
