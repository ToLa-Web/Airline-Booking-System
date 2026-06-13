package com.tola.payload.request;

import com.tola.enums.AircraftStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftRequest {

    @NotBlank(message = "Aircraft code is required")
    private String code;

    @NotBlank(message = "Aircraft model is required")
    private String model;

    @NotBlank(message = "Aircraft manufacturer is required")
    private String manufacturer;

    @Positive(message = "Seating capacity must be a positive")
    @NotNull(message = "Seating capacity is required")
    private Integer seatingCapacity;

    @Positive(message = "Economy seats must be a positive")
    private Integer economySeats;

    @Positive(message = "Premium economy seats must be a positive")
    private Integer premiumEconomySeats;

    @Positive(message = "Business seats must be a positive")
    private Integer businessSeats;

    @Positive(message = "First class seats must be a positive")
    private Integer firstClassSeat;

    @Positive(message = "Range must be a positive")
    private Integer rangeKm;

    @Positive(message = "Cruising speed must be a positive")
    private Integer cruisingSpeedKmh;

    @Positive(message = "Max altitude must be a positive")
    private Integer maxAltitudeFt;

    @Positive(message = "Year of manufacture must be a positive")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    @NotNull(message = "Aircraft status is required")
    private AircraftStatus status;

    @NotNull(message = "Aircraft availability is required")
    private Boolean isAvailable;

    private Long currentAirportId;
}
