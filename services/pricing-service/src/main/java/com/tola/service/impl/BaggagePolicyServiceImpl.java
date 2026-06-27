package com.tola.service.impl;

import com.tola.mapper.BaggagePolicyMapper;
import com.tola.model.BaggagePolicy;
import com.tola.model.Fare;
import com.tola.payload.request.BaggagePolicyRequest;
import com.tola.payload.response.BaggagePolicyResponse;
import com.tola.repository.BaggagePolicyRepository;
import com.tola.repository.FareRepository;
import com.tola.service.BaggagePolicyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaggagePolicyServiceImpl implements BaggagePolicyService {

    private final FareRepository fareRepository;
    private final BaggagePolicyRepository baggagePolicyRepository;
    @Override
    public BaggagePolicyResponse createBaggagePolicy(BaggagePolicyRequest request) throws Exception {
        Fare fare = fareRepository.findById(request.getFareId()).orElseThrow(
                () -> new Exception("Fare not found with id")
        );
        if (baggagePolicyRepository.existsByFareId(fare.getId())) {
            throw new Exception("Baggage policy already exists for this fare.");
        }
        BaggagePolicy baggagePolicy = BaggagePolicyMapper.toEntity(request, fare);
        baggagePolicyRepository.save(baggagePolicy);
        return BaggagePolicyMapper.toResponse(baggagePolicy);
    }

    @Override
    public BaggagePolicyResponse getBaggagePolicyById(Long id) throws Exception {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id).orElseThrow(
                () -> new Exception("Baggage policy not found with id")
        );
        return BaggagePolicyMapper.toResponse(baggagePolicy);
    }

    @Override
    public BaggagePolicyResponse getBaggagePolicyByFareId(Long fareId) {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findByFareId(fareId);
        return BaggagePolicyMapper.toResponse(baggagePolicy);
    }

    @Override
    public List<BaggagePolicyResponse> getBaggagePoliciesByAirlineId(Long airlineId) {
        return baggagePolicyRepository.findByAirlineId(airlineId)
                .stream()
                .map(BaggagePolicyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) throws Exception {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id).orElseThrow(
                () -> new Exception("Baggage policy not found")
        );
        BaggagePolicyMapper.updateEntity(request, baggagePolicy);
        baggagePolicyRepository.save(baggagePolicy);
        return BaggagePolicyMapper.toResponse(baggagePolicy);
    }

    @Override
    public void deleteBaggagePolicy(Long id) {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Baggage policy not found")
        );
        baggagePolicyRepository.delete(baggagePolicy);
    }
}
