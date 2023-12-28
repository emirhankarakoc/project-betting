package com.betting.karakoc.service.repo;

public interface MailService {
    String mailSender2(String token,Long betroundId);
    String forgotPassword(String username);

}
