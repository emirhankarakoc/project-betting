package com.betting.karakoc.service.abstracts;

public interface MailSenderService {
    String mailSenderByBetRoundId(Long betroundId);

    String forgotPassword(String username);

}
