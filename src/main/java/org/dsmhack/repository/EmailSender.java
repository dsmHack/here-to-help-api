package org.dsmhack.repository;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class EmailSender {

  @Value("${sendgrid.api.key}")
  private String sendGridApiKey;

  private String templateId = "b103a74a-2548-4095-9cb7-2c3c7ad0699a";

  public void sendTo(String emailAddress, String loginToken) {
    Email from = new Email("from@dsmhack.org");
    Email to = new Email(emailAddress);
    Content content = new Content("text/html", "hello");
    Mail mail = new Mail(from, "subject", to, content);
    mail.setTemplateId(templateId);
    mail.personalization.get(0).addSubstitution(":loginToken:", loginToken);
    mail.personalization.get(0).addSubstitution(
        ":loginLink:", "localhost:4200/login-confirm/" + loginToken);

    SendGrid sendGrid = new SendGrid(sendGridApiKey);
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