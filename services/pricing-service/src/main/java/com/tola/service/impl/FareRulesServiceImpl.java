package com.tola.service.impl;

import com.tola.mapper.FareMapper;
import com.tola.mapper.FareRulesMapper;
import com.tola.model.Fare;
import com.tola.model.FareRules;
import com.tola.payload.request.FareRulesRequest;
import com.tola.payload.response.FareRulesResponse;
import com.tola.repository.FareRepository;
import com.tola.repository.FareRulesRepository;
import com.tola.service.FareRulesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FareRulesServiceImpl implements FareRulesService {
    private final FareRepository fareRepository;
    private final FareRulesRepository fareRulesRepository;
    @Override
    public FareRulesResponse createFareRules(FareRulesRequest request) throws Exception {
        Fare fare = fareRepository.findById(request.getFareId()).orElseThrow(
                () -> new Exception("Fare id not found.")
        );
        if (fareRulesRepository.existsByFareId(fare.getId())) {
            throw new Exception("Fare rules already exist");
        }
        FareRules fareRules = FareRulesMapper.toEntity(request, fare);
        fareRulesRepository.save(fareRules);

        return FareRulesMapper.toResponse(fareRules);
    }

    @Override
    public FareRulesResponse getFareRulesById(Long id) throws Exception {
        FareRules fareRules = fareRulesRepository.findById(id).orElseThrow(
                () -> new Exception("Fare rules not found with id")
        );
        return FareRulesMapper.toResponse(fareRules);
    }

    @Override
    public FareRulesResponse getFareRulesByFareId(Long fareId) {
        FareRules fareRules = fareRulesRepository.findByFareId(fareId);

        return FareRulesMapper.toResponse(fareRules);
    }

    @Override
    public List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId) {

        return fareRulesRepository.findByAirlineId(airlineId)
                .stream()
                .map(FareRulesMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FareRulesResponse updateFareRules(Long id, FareRulesRequest request) throws Exception {
        FareRules fareRules = fareRulesRepository.findById(id).orElseThrow(
                () -> new Exception("Fare rules not found with id")
        );
        FareRulesMapper.updateEntity(request, fareRules);
        FareRules savedFarerules = fareRulesRepository.save(fareRules);
        return FareRulesMapper.toResponse(savedFarerules);
    }

    @Override
    public void deleteFareRules(Long id) {
        FareRules fareRules = fareRulesRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Fare rules not found with id")
        );
        fareRulesRepository.delete(fareRules);
    }
}
