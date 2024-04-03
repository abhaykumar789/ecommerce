package com.ecommerce.mailservice.service;

public interface EmailService {
    boolean sendEmail(String subject, String message, String to);
}
