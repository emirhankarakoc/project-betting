package com.betting.karakoc.service.interfaces;

public interface MailSenderService {
    String mailSenderByBetRoundId(Long betroundId);

    String forgotPassword(String username);

}
