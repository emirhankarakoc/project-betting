package com.betting.karakoc.service.interfaces;

import com.betting.karakoc.models.requests.MailSenderByBetRoundIdRequest;

public interface MailSenderService {
    String mailSenderByBetRoundId(MailSenderByBetRoundIdRequest request);

    String forgotPassword(String username);

}
