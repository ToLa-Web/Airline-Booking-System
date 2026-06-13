package com.tola.service.impl;

import com.tola.mapper.FlightInstanceMapper;
import com.tola.model.Flight;
import com.tola.model.FlightInstance;
import com.tola.payload.request.FlightInstanceRequest;
import com.tola.payload.response.AircraftResponse;
import com.tola.payload.response.AirlineResponse;
import com.tola.payload.response.AirportResponse;
import com.tola.payload.response.FlightInstanceResponse;
import com.tola.repository.FlightInstanceRepository;
import com.tola.repository.FlightRepository;
import com.tola.service.FlightInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightRepository flightRepository;
    @Override
    public FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest request) throws Exception {
        // TODO: watch airlineId
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new Exception("Flight not found"));

        // TODO: service to service communication
        AircraftResponse aircraft = AircraftResponse.builder()
                .id(1L)
                .totalSeats(180)
                .build();

        FlightInstance flightInstance = FlightInstanceMapper.toEntity(request, flight);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());

        FlightInstance savedFlightInstance = flightInstanceRepository.save(flightInstance);
        //  TODO: create seat instances
        return convertToFlightInstanceResponse(savedFlightInstance);
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) throws Exception {
        FlightInstance flightInstance = flightInstanceRepository.findById(id).orElseThrow(
                () -> new Exception("Flight instance not found with id")
        );
        // TODO: watch airlineId
        return convertToFlightInstanceResponse(flightInstance);
    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(Long airlineId,
                                                       Long departureAirportId,
                                                       Long arrivalAirportId,
                                                       Long flightId,
                                                       LocalDate onDate,
                                                       Pageable pageable) {
        // TODO: watch airlineId
        LocalDateTime start = onDate != null ? onDate.atStartOfDay() : null;
        LocalDateTime end = onDate != null ? onDate.plusDays(1).atStartOfDay() : null;
        return flightInstanceRepository.findByAirlineId(airlineId, departureAirportId, arrivalAirportId, flightId, start, end, pageable)
                .map(this::convertToFlightInstanceResponse);
    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception {
        FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                () -> new Exception("Flight instance not found with id")
        );
        FlightInstanceMapper.updateEntity(request, existing);
        return convertToFlightInstanceResponse(flightInstanceRepository.save(existing));
    }

    @Override
    public void deleteFlightInstance(Long id) throws Exception {
        FlightInstance existing = flightInstanceRepository.findById(id).orElseThrow(
                () -> new Exception("Flight instance not found with id")
        );
        flightInstanceRepository.delete(existing);
    }

    private FlightInstanceResponse convertToFlightInstanceResponse(FlightInstance flightInstance) {
        AirlineResponse airline = AirlineResponse.builder()
                .id(flightInstance.getAirlineId())
                .build();
        AirportResponse departureAirport = AirportResponse.builder()
                .id(flightInstance.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flightInstance.getArrivalAirportId())
                .build();
        AircraftResponse aircraftResponse = AircraftResponse.builder()
                .id(flightInstance.getFlight().getAircraftId())
                .build();

        return FlightInstanceMapper.toResponse(flightInstance,
                aircraftResponse,
                airline,
                departureAirport,
                arrivalAirport
        );
    }
}
