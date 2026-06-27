package com.tola.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaggagePolicyRequest {

    @NotBlank(message = "Baggage policy name is required")
    private String name;

    @NotNull(message = "Fare ID is required")
    private Long fareId;

    private String description;

    @PositiveOrZero(message = "Cabin baggage max weight must be a positive value or zero")
    private Double cabinBaggageMaxWeight;

    @PositiveOrZero(message = "Cabin baggage pieces must be a positive value or zero")
    private Integer cabinBaggagePieces;

    @PositiveOrZero
    private Double cabinBaggageWeightPerPiece;

    @PositiveOrZero
    private Integer cabinBaggageMaxDimension;

    @PositiveOrZero
    private Double checkInBaggageMaxWeight;

    @PositiveOrZero
    private Integer checkInBaggagePieces;

    @PositiveOrZero
    private Double checkInBaggageWeightPerPiece;

    @PositiveOrZero
    private Integer freeCheckedBagsAllowance;

    private Boolean priorityBaggage;
    private Boolean extraBaggageAllowance;
}
