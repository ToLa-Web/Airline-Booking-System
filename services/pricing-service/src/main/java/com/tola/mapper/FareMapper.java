package com.tola.mapper;

import com.tola.embeddable.*;
import com.tola.model.Fare;
import com.tola.payload.request.FareRequest;
import com.tola.payload.response.FareResponse;

public class FareMapper {

    public static Fare toEntity(FareRequest request) {
        if (request == null) return null;

        Double calculatedPrice = request.getCurrentPrice();
        if (calculatedPrice == null) {
            calculatedPrice = request.getBaseFare() + request.getTaxesAndFees() + request.getAirlineFees();
        };

        SeatBenefits seatBenefits = SeatBenefits.builder()
                .extraSeatSpace(bool(request.getExtraSeatSpace()))
                .preferredSeatChoice(bool(request.getPreferredSeatChoice()))
                .advancedSeatSelection(bool(request.getAdvancedSeatSelection()))
                .guaranteedSeatTogether(bool(request.getGuaranteedSeatTogether()))
                .build();

        BoardingBenefits boardingBenefits = BoardingBenefits.builder()
                .priorityBoarding(bool(request.getPriorityBoarding()))
                .priorityCheckin(bool(request.getPriorityCheckin()))
                .fastTrackSecurity(bool(request.getFastTrackSecurity()))
                .build();

        InFlightBenefits inFlightBenefits = InFlightBenefits.builder()
                .complimentaryMeals(bool(request.getComplimentaryMeals()))
                .premiumMealChoice(bool(request.getPremiumMealChoice()))
                .inFlightInternet(bool(request.getInFlightInternet()))
                .inFlightEntertainment(bool(request.getInFlightEntertainment()))
                .complimentaryBeverages(bool(request.getComplimentaryBeverages()))
                .build();

        FlexibilityBenefits flexibilityBenefits = FlexibilityBenefits.builder()
                .freeDateChange(bool(request.getFreeDateChange()))
                .partialRefund(bool(request.getPartialRefund()))
                .fullRefund(bool(request.getFullRefund()))
                .build();

        PremiumServiceBenefits premiumServiceBenefits = PremiumServiceBenefits.builder()
                .loungeAccess(bool(request.getLoungeAccess()))
                .airportTransfer(bool(request.getAirportTransfer()))
                .build();

        return Fare.builder()
                .name(request.getName())
                .rbdCode(request.getRbdCode())
                .flightId(request.getFlightId())
                .cabinClassId(request.getCabinClassId())
                .baseFare(request.getBaseFare())
                .taxesAndFees(request.getTaxesAndFees())
                .airlineFees(request.getAirlineFees())
                .currentPrice(calculatedPrice)
                .fareLabel(request.getFareLabel())
                .seatBenefits(seatBenefits)
                .boardingBenefits(boardingBenefits)
                .inFlightBenefits(inFlightBenefits)
                .flexibilityBenefits(flexibilityBenefits)
                .premiumServiceBenefits(premiumServiceBenefits)
                .build();
    }

    public static FareResponse toResponse(Fare fare) {
        if (fare == null) return null;

        return FareResponse.builder()
                .id(fare.getId())
                .name(fare.getName())
                .rbdCode(fare.getRbdCode())
                .flightId(fare.getFlightId())
                .cabinClassId(fare.getCabinClassId())
                .cabinClass(fare.getCabinClass())
                .baseFare(fare.getBaseFare())
                .taxesAndFees(fare.getTaxesAndFees())
                .airlineFees(fare.getAirlineFees())
                .currentPrice(fare.getCurrentPrice())
                .totalPrice(fare.getTotalPrice())
                .fareLabel(fare.getFareLabel())

                // from fareRules
                .fareRulesId(fare.getFareRules() != null ? fare.getFareRules().getId() : null)

                // Seat benefits
                .extraSeatSpace(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getExtraSeatSpace() : false)
                .preferredSeatChoice(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getPreferredSeatChoice() : false)
                .advancedSeatSelection(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getAdvancedSeatSelection() : false)
                .guaranteedSeatTogether(fare.getSeatBenefits() != null ? fare.getSeatBenefits().getGuaranteedSeatTogether() : false)
                // Boarding benefits
                .priorityBoarding(fare.getBoardingBenefits() != null ? fare.getBoardingBenefits().getPriorityBoarding() : false)
                .priorityCheckin(fare.getBoardingBenefits() != null ? fare.getBoardingBenefits().getPriorityCheckin() : false)
                .fastTrackSecurity(fare.getBoardingBenefits() != null ? fare.getBoardingBenefits().getFastTrackSecurity() : false)
                // In flight benefits
                .complimentaryMeals(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getComplimentaryMeals() : false)
                .premiumMealChoice(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getPremiumMealChoice() : false)
                .inFlightInternet(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getInFlightInternet() : false)
                .inFlightEntertainment(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getInFlightEntertainment() : false)
                .complimentaryBeverages(fare.getInFlightBenefits() != null ? fare.getInFlightBenefits().getComplimentaryBeverages() : false)
                // Flexibility benefits
                .freeDateChange(fare.getFlexibilityBenefits() != null ? fare.getFlexibilityBenefits().getFreeDateChange() : false)
                .partialRefund(fare.getFlexibilityBenefits() != null ? fare.getFlexibilityBenefits().getPartialRefund() : false)
                .fullRefund(fare.getFlexibilityBenefits() != null ? fare.getFlexibilityBenefits().getFullRefund() : false)
                // Premium service benefits
                .loungeAccess(fare.getPremiumServiceBenefits() != null ? fare.getPremiumServiceBenefits().getLoungeAccess() : false)
                .airportTransfer(fare.getPremiumServiceBenefits() != null ? fare.getPremiumServiceBenefits().getAirportTransfer() : false)

                // Nested Response
                .fareRules(fare.getFareRules() != null ? FareRulesMapper.toResponse(fare.getFareRules()) : null)
                .baggagePolicy(fare.getBaggagePolicy() != null ? BaggagePolicyMapper.toResponse(fare.getBaggagePolicy()) : null)

                .createdAt(fare.getCreatedAt())
                .updatedAt(fare.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FareRequest request, Fare existing) {
        if (request == null || existing == null) return;

        if (request.getName() != null) existing.setName(request.getName());
        if (request.getRbdCode() != null) existing.setRbdCode(request.getRbdCode());
        if (request.getFlightId() != null) existing.setFlightId(request.getFlightId());
        if (request.getCabinClassId() != null) existing.setCabinClassId(request.getCabinClassId());

        if (request.getBaseFare() != null) existing.setBaseFare(request.getBaseFare());
        if (request.getTaxesAndFees() != null) existing.setTaxesAndFees(request.getTaxesAndFees());
        if (request.getAirlineFees() != null) existing.setAirlineFees(request.getAirlineFees());
        if (request.getCurrentPrice() != null) existing.setCurrentPrice(request.getCurrentPrice());
        if (request.getFareLabel() != null) existing.setFareLabel(request.getFareLabel());

        SeatBenefits seatBenefits = existing.getSeatBenefits();
        if (request.getExtraSeatSpace() != null) seatBenefits.setExtraSeatSpace(request.getExtraSeatSpace());
        if (request.getPreferredSeatChoice() != null) seatBenefits.setPreferredSeatChoice(request.getPreferredSeatChoice());
        if (request.getAdvancedSeatSelection() != null) seatBenefits.setAdvancedSeatSelection(request.getAdvancedSeatSelection());
        if (request.getGuaranteedSeatTogether() != null) seatBenefits.setGuaranteedSeatTogether(request.getGuaranteedSeatTogether());

        BoardingBenefits boardingBenefits = existing.getBoardingBenefits();
        if (request.getPriorityBoarding() != null) boardingBenefits.setPriorityBoarding(request.getPriorityBoarding());
        if (request.getPriorityCheckin() != null) boardingBenefits.setPriorityCheckin(request.getPriorityCheckin());
        if (request.getFastTrackSecurity() != null) boardingBenefits.setFastTrackSecurity(request.getFastTrackSecurity());

        InFlightBenefits inFlightBenefits = existing.getInFlightBenefits();
        if (request.getComplimentaryMeals() != null) inFlightBenefits.setComplimentaryMeals(request.getComplimentaryMeals());
        if (request.getPremiumMealChoice() != null) inFlightBenefits.setPremiumMealChoice(request.getPremiumMealChoice());
        if (request.getInFlightInternet() != null) inFlightBenefits.setInFlightInternet(request.getInFlightInternet());
        if (request.getInFlightEntertainment() != null) inFlightBenefits.setInFlightEntertainment(request.getInFlightEntertainment());
        if (request.getComplimentaryBeverages() != null) inFlightBenefits.setComplimentaryBeverages(request.getComplimentaryBeverages());

        FlexibilityBenefits flexibilityBenefits = existing.getFlexibilityBenefits();
        if (request.getFreeDateChange() != null) flexibilityBenefits.setFreeDateChange(request.getFreeDateChange());
        if (request.getPartialRefund() != null) flexibilityBenefits.setPartialRefund(request.getPartialRefund());
        if (request.getFullRefund() != null) flexibilityBenefits.setFullRefund(request.getFullRefund());

        PremiumServiceBenefits premiumServiceBenefits = existing.getPremiumServiceBenefits();
        if (request.getLoungeAccess() != null) premiumServiceBenefits.setLoungeAccess(request.getLoungeAccess());
        if (request.getAirportTransfer() != null) premiumServiceBenefits.setAirportTransfer(request.getAirportTransfer());
    }

    private static boolean bool(Boolean value) {
        return value != null ? value : false;
    }
}
