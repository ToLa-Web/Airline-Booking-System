package com.tola.mapper;

import com.tola.model.Flight;
import com.tola.model.FlightSchedule;
import com.tola.payload.request.FlightScheduleRequest;
import com.tola.payload.response.AirportResponse;
import com.tola.payload.response.FlightScheduleResponse;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(FlightScheduleRequest request, Flight flight) {

        if (request == null || flight == null) {return null;}

        return FlightSchedule.builder()
                .flight(flight)
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .operatingDays(request.getOperatingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static FlightScheduleResponse toResponse(FlightSchedule flightSchedule,
                                                   AirportResponse arrival,
                                                   AirportResponse departure) {

        if (flightSchedule == null) return null;
        return FlightScheduleResponse.builder()
                .id(flightSchedule.getId())
                .flightId(flightSchedule.getFlight() != null ? flightSchedule.getFlight().getId() : null)
                .flightNumber(flightSchedule.getFlight() != null ? flightSchedule.getFlight().getFlightNumber() : null)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureTime(flightSchedule.getDepartureTime())
                .arrivalTime(flightSchedule.getArrivalTime())
                .startDate(flightSchedule.getStartDate())
                .endDate(flightSchedule.getEndDate())
                .operatingDays(flightSchedule.getOperatingDays())
                .isActive(flightSchedule.getIsActive())
                .build();

    }

    public static void updateEntity(FlightScheduleRequest request, FlightSchedule existing) {
        if (request == null || existing == null) return;

        if (request.getDepartureTime() != null) existing.setDepartureTime(request.getDepartureTime());
        if (request.getArrivalTime() != null) existing.setArrivalTime(request.getArrivalTime());
        if (request.getStartDate() != null) existing.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) existing.setEndDate(request.getEndDate());
        if (request.getOperatingDays() != null) existing.setOperatingDays(request.getOperatingDays());
        if (request.getIsActive() != null) existing.setIsActive(request.getIsActive());
    }
}
