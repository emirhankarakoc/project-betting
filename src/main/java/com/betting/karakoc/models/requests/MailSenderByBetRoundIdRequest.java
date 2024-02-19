package com.betting.karakoc.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MailSenderByBetRoundIdRequest {
    private String betroundId;
    private String adminToken;
}
