package org.dsmhack.repository;

import com.sendgrid.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class EmailSender {

    private static final String SEND_GRID_API_KEY_PLEASE_CHANGE = "G.3oCPE_fzQ9OKkIREyMsKbg.Hs7bpk88QxnxG9BEG-l2i-1AxHMjnspsspoWYS4j9V8";
    private String TEMPLATE_ID = "sendGridTemplateIdGoesHere";

    public void sendTo(String emailAddress, String loginToken) {
        Email from = new Email("from@dsmhack.org");
        Email to = new Email(emailAddress);
        Content content = new Content("text/html", "");
        Mail mail = new Mail(from, "", to, content);
        mail.setTemplateId(TEMPLATE_ID);
        mail.personalization.get(0).addSubstitution(":loginToken:", loginToken);

        SendGrid sendGrid = new SendGrid(SEND_GRID_API_KEY_PLEASE_CHANGE);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}