package com.lucas.slbackend.service;

public interface MailService {
  void send(String to, String subject, String htmlBody);
}
