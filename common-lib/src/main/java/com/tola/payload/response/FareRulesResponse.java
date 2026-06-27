package com.tola.payload.response;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FareRulesResponse {
    private Long id;
    private String ruleName;
    private Long fareId;
    private Long airlineId;
    private Boolean isRefundable;
    private Double changeFee;
    private Double cancellationFee;
    private Integer refundDeadlineDays;
    private Integer changeDeadlineHours;
    private Boolean isChangeable;
    private Instant createdAt;
    private Instant updatedAt;
}
