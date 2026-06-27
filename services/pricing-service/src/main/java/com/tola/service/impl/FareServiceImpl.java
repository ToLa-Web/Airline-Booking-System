package com.tola.service.impl;

import com.tola.mapper.FareMapper;
import com.tola.model.Fare;
import com.tola.payload.request.FareRequest;
import com.tola.payload.response.FareResponse;
import com.tola.repository.FareRepository;
import com.tola.service.FareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;
    @Override
    public FareResponse createFare(FareRequest request) throws Exception {
        if (fareRepository.existsByFlightIdAndCabinClassIdAndName(request.getFlightId(),
                request.getCabinClassId(), request.getName())) {
            throw new Exception("Fare with the same name already exists for this flight and cabin class.");
        }
        Fare fare = FareMapper.toEntity(request);
        fare = fareRepository.save(fare);
        return FareMapper.toResponse(fare);
    }

    @Override
    public FareResponse getFareById(Long id) throws Exception {
        Fare fare = fareRepository.findById(id).orElseThrow(
                () -> new Exception("Fare not found with id")
        );

        return FareMapper.toResponse(fare);
    }

    @Override
    public List<FareResponse> getFaresByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) {
        return fareRepository.findByFlightIdAndCabinClassId(flightId, cabinClassId)
                .stream()
                .map(FareMapper::toResponse)
                .toList();
    }

    @Override
    public FareResponse updateFare(Long id, FareRequest request) throws Exception {
        Fare fare = fareRepository.findById(id).orElseThrow(
                () -> new Exception("Fare not found with id")
        );
        if (fareRepository.existsByFlightIdAndCabinClassIdAndNameAndIdNot(
                request.getFlightId(),
                request.getCabinClassId(),
                request.getName(),
                fare.getId())) {
            throw new Exception("Fare with the same name already exists for this flight and cabin class.");
        }
        FareMapper.updateEntity(request, fare);
        Fare updatedFare = fareRepository.save(fare);
        return FareMapper.toResponse(updatedFare);
    }

    @Override
    public void deleteFare(Long id) throws Exception {
        Fare fare = fareRepository.findById(id).orElseThrow(
                () -> new Exception("Fare not found with id")
        );
        fareRepository.delete(fare);
    }

    @Override
    public List<Fare> getFares() {
        return fareRepository.findAll();
    }

    @Override
    public Map<Long, FareResponse> getLowestFarePerFlight(List<Long> flightIds, Long cabinClassId) {
        if (flightIds == null || flightIds.isEmpty()) return Map.of();

        List<Fare> fares = fareRepository.findByFlightIdInAndCabinClassId(
                flightIds, cabinClassId
        );
        Map<Long, FareResponse> cheapestFares  = fares.stream()
                .collect(Collectors.toMap(
                Fare::getFlightId,
                fare -> fare,
                (existing, candidate) ->
                        candidate.getTotalPrice() < existing.getTotalPrice()
                                ? candidate
                                : existing
                )).entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                e -> FareMapper.toResponse(e.getValue())
        ));
        return cheapestFares;
    }

    @Override
    public Map<Long, FareResponse> getFaresByIds(List<Long> ids) {
        List<Fare> fares = fareRepository.findAllById(ids);
        return fares.stream().collect(Collectors.toMap(Fare::getId, FareMapper::toResponse));
    }
}
