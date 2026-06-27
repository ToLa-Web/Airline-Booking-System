package com.tola.payload.response;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaggagePolicyResponse {
    private Long id;

    private String name;
    private String description;

    //cabin baggage
    private Double cabinBaggageMaxWeight;
    private Integer cabinBaggagePieces;
    private Double cabinBaggageWeightPerPiece;
    private Integer cabinBaggageMaxDimension;

    //check-in baggage
    private Double checkInBaggageMaxWeight;
    private Integer checkInBaggagePieces;
    private Double checkInBaggageWeightPerPiece;
    private Integer freeCheckedBagsAllowance;

    //benefits
    private Boolean priorityBaggage;
    private Boolean extraBaggageAllowance;

    //relationship
    private Long airlineId;
    private Long fareId;

    //Audit
    private Instant createdAt;
    private Instant updatedAt;

}
