package com.tola.payload.request;

import com.tola.enums.CabinClassType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FareRequest {

    @NotBlank(message = "Fare name is required")
    private String name;

    @NotNull(message = "RBD is required")
    private Character rbdCode;

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @NotNull(message = "Cabin class ID is required")
    private Long cabinClassId;

    private CabinClassType cabinClass;

    @NotNull(message = "Base fare is required")
    @Positive(message = "Base fare must be a positive value")
    private Double baseFare;

    private Double taxesAndFees;
    private Double airlineFees;
    private Double currentPrice;

    @Size(max = 100)
    private String fareLabel;

    // Seat benefits
    private Boolean extraSeatSpace;
    private Boolean preferredSeatChoice;
    private Boolean advancedSeatSelection;
    private Boolean guaranteedSeatTogether;

    // Boarding benefits
    private Boolean priorityBoarding;
    private Boolean priorityCheckin;
    private Boolean fastTrackSecurity;

    // in flight benefits
    private Boolean complimentaryMeals;
    private Boolean premiumMealChoice;
    private Boolean inFlightInternet;
    private Boolean inFlightEntertainment;
    private Boolean complimentaryBeverages;

    //Flexibility benefits
    private Boolean freeDateChange;
    private Boolean partialRefund;
    private Boolean fullRefund;

    // premium benefits
    private Boolean loungeAccess;
    private Boolean airportTransfer;
}
