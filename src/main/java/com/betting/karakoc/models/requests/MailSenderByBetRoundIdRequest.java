package com.betting.karakoc.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailSenderByBetRoundIdRequest {
    private Long betroundId;
    private String adminToken;
}
