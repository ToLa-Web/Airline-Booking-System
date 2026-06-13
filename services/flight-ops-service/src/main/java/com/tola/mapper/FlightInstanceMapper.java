package com.tola.mapper;

import com.tola.enums.FlightStatus;
import com.tola.model.Flight;
import com.tola.model.FlightInstance;
import com.tola.payload.request.FlightInstanceRequest;
import com.tola.payload.response.AircraftResponse;
import com.tola.payload.response.AirlineResponse;
import com.tola.payload.response.AirportResponse;
import com.tola.payload.response.FlightInstanceResponse;

public class FlightInstanceMapper {

    public static FlightInstance toEntity(FlightInstanceRequest request, Flight flight) throws Exception{
        if (flight == null) return null;

        return FlightInstance.builder()
                .flight(flight)
                .airlineId(flight.getAirlineId())
                .scheduleId(request.getScheduleId())
                .departureAirportId(
                        request.getDepartureAirportId() != null ? request.getDepartureAirportId() : null
                ).arrivalAirportId(
                        request.getArrivalAirportId() != null ? request.getArrivalAirportId() : null
                ).departureDateTime(request.getDepartureDateTime())
                .arrivalDateTime(request.getArrivalDateTime())
                .status(FlightStatus.SCHEDULED)
                .minAdvanceBookingDays(request.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(request.getMaxAdvanceBookingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static FlightInstanceResponse toResponse(FlightInstance flightInstance,
                                                  AircraftResponse aircraftResponse,
                                                  AirlineResponse airline,
                                                  AirportResponse departureAirport,
                                                  AirportResponse arrivalAirport
                                                  ){
        if (flightInstance == null) return null;

        return FlightInstanceResponse.builder()
                .id(flightInstance.getId())
                .flightId(flightInstance.getFlight() != null ? flightInstance.getFlight().getId() : null)
                .flightNumber(flightInstance.getFlight() != null ? flightInstance.getFlight().getFlightNumber() : null)
                .aircraftId(flightInstance.getFlight().getAircraftId())
                .aircraftModel(aircraftResponse.getModel())
                .aircraftCode(aircraftResponse.getCode())
                .airlineId(flightInstance.getAirlineId())
                .airlineName(airline.getName())
                .airlineLogo(airline.getLogoUrl())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDateTime(flightInstance.getDepartureDateTime())
                .arrivalDateTime(flightInstance.getArrivalDateTime())
                .formattedDuration(flightInstance.getFormattedDuration())
                .totalSeats(flightInstance.getTotalSeats())
                .availableSeats(flightInstance.getAvailableSeats())
                .status(flightInstance.getStatus())
                .minAdvanceBookingDay(flightInstance.getMinAdvanceBookingDays())
                .maxAdvanceBookingDay(flightInstance.getMaxAdvanceBookingDays())
                .isActive(flightInstance.isActive())
                .build();
    }

    public static void updateEntity(FlightInstanceRequest request, FlightInstance existing){
        if (request == null || existing == null) return;

        if (request.getDepartureAirportId() != null) existing.setDepartureAirportId(request.getDepartureAirportId());
        if (request.getArrivalAirportId() != null) existing.setArrivalAirportId(request.getArrivalAirportId());
        if (request.getDepartureDateTime() != null) existing.setDepartureDateTime(request.getDepartureDateTime());
        if (request.getArrivalDateTime() != null) existing.setArrivalDateTime(request.getArrivalDateTime());
        if (request.getAvailableSeats() != null)  existing.setAvailableSeats(request.getAvailableSeats());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());
        if (request.getMinAdvanceBookingDays() != null) existing.setMinAdvanceBookingDays(request.getMinAdvanceBookingDays());
        if (request.getMaxAdvanceBookingDays() != null) existing.setMaxAdvanceBookingDays(request.getMaxAdvanceBookingDays());
        if (request.getIsActive() != null) existing.setActive(request.getIsActive());
    }
}
