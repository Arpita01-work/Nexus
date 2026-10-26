
package com.arpita.nexus.authentication.email;

import com.arpita.nexus.user.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Override
    public void sendActivationEmail(
        User user,
        String activationToken) {

        String activationLink = frontendUrl
            + "/activate?token="
            + activationToken;

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                message,
                true,
                "UTF-8"
            );

            helper.setFrom(senderEmail, "Nexus");
            helper.setTo(user.getEmail());
            helper.setSubject("Activate your Nexus account");

            String firstName = user.getFirstName() == null
                ? "there"
                : user.getFirstName();

            String html = """
                    <!DOCTYPE html>
                    <html>
                    <body style="font-family: Arial, sans-serif; color: #222;">
                        <div style="max-width: 600px; margin: auto; padding: 24px;">
                            <h2>Welcome to Nexus, %s!</h2>
                            <p>Your account has been created by your organization administrator.</p>
                            <p>To get started, set a password for your account using the button below.</p>
                            <p style="margin: 32px 0;">
                                <a href="%s"
                                   style="background-color: #4f46e5;
                                          color: #ffffff;
                                          padding: 12px 24px;
                                          text-decoration: none;
                                          border-radius: 6px;
                                          display: inline-block;">
                                    Set Your Password
                                </a>
                            </p>
                            <p>If the button does not work, copy this link into your browser:</p>
                            <p><a href="%s">%s</a></p>
                            <p>This activation link expires in 24 hours.</p>
                            <p>If you were not expecting this invitation, you can ignore this email.</p>
                            <p>Regards,<br/>The Nexus Team</p>
                        </div>
                    </body>
                    </html>
                    """.formatted(
                escapeHtml(firstName),
                activationLink,
                activationLink,
                activationLink
            );

            helper.setText(html, true);

            mailSender.send(message);

            log.info(
                "Account activation email sent to {}",
                user.getEmail()
            );

        } catch (MessagingException | MailException ex) {
            log.error(
                "Failed to send activation email to {}",
                user.getEmail(),
                ex
            );

            throw new IllegalStateException(
                "Unable to send account activation email.",
                ex
            );
        } catch (java.io.UnsupportedEncodingException ex) {
            throw new IllegalStateException(
                "Unable to configure the Nexus sender email.",
                ex
            );
        }
    }

    private String escapeHtml(String value) {
        return value
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }
}
