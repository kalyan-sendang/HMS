package com.java.hospitalmanagementsystem.security.Implementation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.util.Map;

/**
 * Service class for sending emails.
 *
 * <p>This class provides functionality to send emails using SendGrid's API. It encapsulates the
 * process of setting up email content and dispatching the email to the specified recipient.
 */
@Service
@Slf4j
public class EmailSender {

  private final JavaMailSender javaMailSender;

  public EmailSender(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Value("${email.from}")
  private String emailFrom;



  /**
   * Sends an email to the specified recipient.
   *
   * @param to            the recipient's email address
   * @param templateId    the ID of the email template to use
   * @param substitutions a map of substitutions to apply to the email template
   */
  public void sendEmail(String to, String templateId, Map<String, String> substitutions) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

    try {
      // Set sender, recipient, subject, and email content with substitutions
      helper.setFrom(emailFrom);
      helper.setTo(to);
      helper.setSubject("Your Email Subject");

      StringBuilder emailContent = new StringBuilder();
      emailContent.append("Hello,\n\n");
      emailContent.append("This is the email content with substitutions:\n");
      for (Map.Entry<String, String> entry : substitutions.entrySet()) {
        emailContent.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
      }
      helper.setText(emailContent.toString());

      // Send the email
      javaMailSender.send(mimeMessage);

      log.info("Email sent successfully to {}", to);
    } catch (MessagingException e) {
      log.error("Error sending email: {}", e.getMessage(), e);
    }
  }
}