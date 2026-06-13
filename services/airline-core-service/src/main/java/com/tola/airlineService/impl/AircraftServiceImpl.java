package com.tola.airlineService.impl;

import com.tola.airlineService.AircraftService;
import com.tola.mapper.AircraftMapper;
import com.tola.model.Aircraft;
import com.tola.model.Airline;
import com.tola.payload.request.AircraftRequest;
import com.tola.payload.response.AircraftResponse;
import com.tola.repository.AircraftRepository;
import com.tola.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for ownerId"));
        Aircraft aircraft = AircraftMapper.toEntity(request, airline);

        if (aircraftRepository. existsByCode(aircraft.getCode())) {
            throw new Exception("Code already exist with another aircraft");
        }

        if (aircraft.getSeatingCapacity() < aircraft.getTotalSeats()) {
            throw new Exception("Total seats cannot exceed seating capacity");
        }

        return AircraftMapper.toResponse(
                aircraftRepository.save(aircraft)
        );
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Aircraft not found with id: " + id)
                );
        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwnerId(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for ownerId: " + ownerId));
        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for ownerId: " + ownerId));
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());
        if (aircraft == null) {
            throw new Exception("Aircraft not found with id");
        }

        if (request.getCode() != null
                &&!aircraft.getCode().equals(request.getCode())
                && aircraftRepository.existsByCode(request.getCode())) {
            throw new Exception("Code already exist with another aircraft");
        }
        AircraftMapper.updateEntity(aircraft, request);
        return AircraftMapper.toResponse(
                aircraftRepository.save(aircraft)
        );
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline not found for ownerId: " + ownerId));
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());
        if (aircraft == null) {
            throw new RuntimeException("Aircraft not found with id");
        }
        aircraftRepository.delete(aircraft);
    }
}
