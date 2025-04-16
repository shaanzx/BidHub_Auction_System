package lk.shaanzx.online_auction_system_backend.service;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String body);
}
