package application.service;

import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Value("${mailersend.sdk.token}")
    private String token;

    public void send(String subject, String text) {
        Email email = new Email();
        email.setFrom("Outside Worx", "info@outsideworx.net");
        email.addRecipient("Outside Worx", "info@outsideworx.net");
        email.setSubject(subject);
        email.setHtml(text);
        MailerSend mailerSend = new MailerSend();
        mailerSend.setToken(token);
        try {
            log.info("Mail sent wih status code: {}", mailerSend.emails().send(email).responseStatusCode);
        } catch (MailerSendException e) {
            throw new IllegalStateException("Email sending failed.", e);
        }
    }
}
