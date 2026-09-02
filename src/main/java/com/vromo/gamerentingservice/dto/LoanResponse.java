package com.vromo.gamerentingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LoanResponse {

    private Long loanId;

    private Long gameId;

    private Long memberId;

    private LocalDateTime loanedAt;

}
