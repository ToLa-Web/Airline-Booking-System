package com.tola.controller;

import com.tola.airlineService.AircraftService;
import com.tola.payload.request.AircraftRequest;
import com.tola.payload.response.AircraftResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest aircraftRequest,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        AircraftResponse aircraft = aircraftService.createAircraft(aircraftRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(
            @PathVariable Long id
    ) throws Exception {
        AircraftResponse aircraft = aircraftService.getAircraftById(id);
        return ResponseEntity.ok(aircraft);
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponse>> listAllAircraftBys(
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.listAllAircraftByOwnerId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraftById(
        @PathVariable Long id,
        @Valid @RequestBody AircraftRequest aircraft,
        @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.updateAircraft(id, aircraft, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraftById(
        @PathVariable Long id,
        @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        aircraftService.deleteAircraft(id, userId);
        return ResponseEntity.noContent().build();
    }
}
