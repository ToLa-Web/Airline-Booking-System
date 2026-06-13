package com.tola.service.impl;

import com.tola.enums.FlightStatus;
import com.tola.mapper.FlightScheduleMapper;
import com.tola.model.Flight;
import com.tola.model.FlightSchedule;
import com.tola.payload.request.FlightInstanceRequest;
import com.tola.payload.request.FlightScheduleRequest;
import com.tola.payload.response.AirportResponse;
import com.tola.payload.response.FlightScheduleResponse;
import com.tola.repository.FlightRepository;
import com.tola.repository.FlightScheduleRepository;
import com.tola.service.FlightInstanceService;
import com.tola.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {
    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;
    private final FlightInstanceService flightInstanceService;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest request) throws Exception {
        //TODO: watch airlineId
        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                () -> new Exception("Flight not found with id"));
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new Exception("End date is before start date");
        }
        FlightSchedule flightSchedule = FlightScheduleMapper.toEntity(request, flight);
        FlightSchedule savedFlightSchedule = flightScheduleRepository.save(flightSchedule);

        List<DayOfWeek> operatingDays = savedFlightSchedule.getOperatingDays();
        LocalDate startDate = savedFlightSchedule.getStartDate();
        LocalDate endDate = savedFlightSchedule.getEndDate();

        FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest.builder()
                .scheduleId(savedFlightSchedule.getId())
                .flightId(request.getFlightId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureAirportId(flight.getDepartureAirportId())
                .status(FlightStatus.SCHEDULED)
                .build();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (operatingDays.contains(date.getDayOfWeek())) {
                flightInstanceRequest.setDepartureDateTime(
                        LocalDateTime.of(date, savedFlightSchedule.getDepartureTime())
                );
                flightInstanceRequest.setArrivalDateTime(
                        LocalDateTime.of(date, savedFlightSchedule.getArrivalTime())
                );
                flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest);
            }
        }
        return convertToFlightScheduleResponse(savedFlightSchedule);
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new Exception("Flight schedule not found with id")
        );
        return convertToFlightScheduleResponse(flightSchedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long airlineId) {
        // TODO: watch airlineId
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findByFlightAirlineId(airlineId);
        return flightSchedules.stream()
                .map(this::convertToFlightScheduleResponse)
                .toList();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest request) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new Exception("Flight schedule not found with id")
        );
        FlightScheduleMapper.updateEntity(request, flightSchedule);
        flightScheduleRepository.save(flightSchedule);
        return convertToFlightScheduleResponse(flightSchedule);
    }

    @Override
    public void deleteFlightSchedule(Long id) throws Exception {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new Exception("Flight schedule not found with id")
        );
        flightScheduleRepository.delete(flightSchedule);
    }

    private FlightScheduleResponse convertToFlightScheduleResponse(FlightSchedule flightSchedule) {
    // TODO: service to service communication
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flightSchedule.getArrivalAirportId())
                .build();
        AirportResponse departureAirport = AirportResponse.builder()
                .id(flightSchedule.getDepartureAirportId())
                .build();
        return FlightScheduleMapper.toResponse(
                flightSchedule,
                arrivalAirport,
                departureAirport);
    }
}
