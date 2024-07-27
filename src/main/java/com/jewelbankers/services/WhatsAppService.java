package com.jewelbankers.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.whatsapp.from}")
    private String fromNumber;

    public void sendWhatsAppMessage(String toNumber, String messageBody, String mediaUrl) {
        Twilio.init(accountSid, authToken);
        Message.creator(new PhoneNumber("whatsapp:" + toNumber),
                new PhoneNumber("whatsapp:" + fromNumber),
                messageBody)
                .setMediaUrl(mediaUrl)
                .create();
    }
}

