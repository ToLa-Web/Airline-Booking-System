package com.tola.service.impl;

import com.tola.enums.FlightStatus;
import com.tola.mapper.FlightMapper;
import com.tola.model.Flight;
import com.tola.payload.request.FlightRequest;
import com.tola.payload.response.AircraftResponse;
import com.tola.payload.response.AirlineResponse;
import com.tola.payload.response.AirportResponse;
import com.tola.payload.response.FlightResponse;
import com.tola.repository.FlightRepository;
import com.tola.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception {
        // TODO: watch airlineId
        if (flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())) {
            throw new Exception("Flight number already exists");
        }
        Flight flight = FlightMapper.toEntity(flightRequest);
        flight.setAirlineId(airlineId);
        Flight savedFlight = flightRepository.save(flight);
        return convertToResponse(savedFlight);
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Pageable pageable) {
        // TODO: watch airlineId
            return flightRepository.findByAirlineId(airlineId,
                            departureAirportId,
                            arrivalAirportId,
                            pageable)
                    .map(this::convertToResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) throws Exception {
        Flight flight = flightRepository.findById(id).orElseThrow(
                () -> new Exception("Flight not found with id")
        );
        // TODO: watch airlineId
        return convertToResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) throws Exception {
        Flight existing = flightRepository.findById(id).orElseThrow(
                () -> new Exception("Flight not found with id")
        );
        if (flightRequest.getFlightNumber() != null &&
            flightRepository.existsByFlightNumberAndIdNot(flightRequest.getFlightNumber(), id)
        ) {
            throw new Exception("Flight number already exists");
        }
        FlightMapper.updateEntity(flightRequest, existing);
        Flight updated = FlightMapper.toEntity(flightRequest);
        return convertToResponse(flightRepository.save(updated));
    }

    @Override
    public FlightResponse changeFlightStatus(Long id, FlightStatus status) throws Exception {
        Flight existing = flightRepository.findById(id).orElseThrow(
                () -> new Exception("Flight not found with id")
        );
        existing.setStatus(status);
        Flight updated = flightRepository.save(existing);
        return convertToResponse(updated);
    }

    @Override
    public void deleteFlight(Long airlineId, Long id) {
        // TODO: watch airlineId
        Flight existing = flightRepository.findByAirlineIdAndId(airlineId, id).orElseThrow(
                () -> new RuntimeException("Flight not found with id")
        );
        flightRepository.delete(existing);

    }

    public FlightResponse convertToResponse(Flight flight) {
        // TODO: watch service to service communication
        AircraftResponse aircraft = AircraftResponse.builder()
                .id(flight.getAircraftId())
                .build();
        AirlineResponse airline = AirlineResponse.builder()
                .id(flight.getAirlineId())
                .build();
        AirportResponse departureAirport = AirportResponse.builder()
                .id(flight.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flight.getArrivalAirportId())
                .build();

        return FlightMapper.toResponse(
                flight,
                aircraft,
                airline,
                departureAirport,
                arrivalAirport);
    }
}
