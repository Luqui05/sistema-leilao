package com.lucas.slbackend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lucas.slbackend.service.MailService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultMailService implements MailService {

  private static final Logger log = LoggerFactory.getLogger(DefaultMailService.class);
  private final JavaMailSender mailSender; // Spring will create a no-op proxy only if configured; if misconfigured we'll catch later.

  @Override
  public void send(String to, String subject, String htmlBody) {
    try {
      MimeMessage mime = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mime, "UTF-8");
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(htmlBody, true);
      mailSender.send(mime);
    } catch (MailException | jakarta.mail.MessagingException ex) {
      // Fail gracefully in dev/test without breaking password flows.
      log.warn("Mail send failed (non-fatal): {}", ex.getMessage());
    } catch (Exception ex) {
      log.warn("Unexpected mail error: {}", ex.getMessage());
    }
  }
}
