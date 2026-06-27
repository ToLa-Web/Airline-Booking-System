package com.tola.controller;

import com.tola.payload.request.FareRulesRequest;
import com.tola.payload.response.ApiResponse;
import com.tola.payload.response.FareRulesResponse;
import com.tola.service.FareRulesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fare-rules")
public class FareRulesController {

    private final FareRulesService fareRulesService;

    @PostMapping()
    public ResponseEntity<FareRulesResponse> createFareRules(@Valid @RequestBody FareRulesRequest request)
            throws Exception {
        FareRulesResponse response = fareRulesService.createFareRules(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FareRulesResponse> getFareRulesById(@PathVariable Long id) throws Exception {
        FareRulesResponse response = fareRulesService.getFareRulesById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<FareRulesResponse> getFareRulesByFareId(@PathVariable Long fareId) {
        FareRulesResponse response = fareRulesService.getFareRulesByFareId(fareId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<FareRulesResponse>> getFareRulesByAirlineId(@PathVariable Long airlineId) {
        return ResponseEntity.ok(fareRulesService.getFareRulesByAirlineId(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareRulesResponse> updateFareRules(
            @PathVariable Long id,
            @Valid @RequestBody FareRulesRequest request
    ) throws Exception {
        FareRulesResponse response = fareRulesService.updateFareRules(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFareRules(@PathVariable Long id) {
        fareRulesService.deleteFareRules(id);
        ApiResponse response = new ApiResponse("Fare rules successfully deleted");
        return ResponseEntity.ok(response);
    }
}
