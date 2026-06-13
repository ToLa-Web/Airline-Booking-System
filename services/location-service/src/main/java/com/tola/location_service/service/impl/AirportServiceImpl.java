package com.tola.location_service.service.impl;

import com.tola.location_service.mapper.AirportMapper;
import com.tola.location_service.model.Airport;
import com.tola.location_service.model.City;
import com.tola.location_service.repository.AirportRepository;
import com.tola.location_service.repository.CityRepository;
import com.tola.location_service.service.AirportService;
import com.tola.payload.request.AirportRequest;
import com.tola.payload.response.AirportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception {
        if (airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with the given IATA code already exists");
        }

        City city = cityRepository.findById(request.getCityId()).orElseThrow(
                () -> new Exception("City not found")
        );
        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);

        Airport saveAirport = airportRepository.save(airport);

        return AirportMapper.toResponse(saveAirport);
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport not exist with the given id")
        );
        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {
        Airport existionAirport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport not exist with the given id")
        );
        if (request.getIataCode() != null
                && !existionAirport.getIataCode().equals(request.getIataCode())
                && airportRepository.findByIataCode(request.getIataCode()).isPresent()
        ) {
            throw new Exception("Airport with the given IATA code already exists");
        }
        AirportMapper.updateEntity(request, existionAirport);
        Airport updatedAirport = airportRepository.save(existionAirport);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        Airport  airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("Airport not exist with the given id")
        );
        airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponse> getAirportsByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId).stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
