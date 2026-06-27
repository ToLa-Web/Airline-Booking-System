package com.tola.controller;

import com.tola.payload.request.FareRequest;
import com.tola.payload.response.ApiResponse;
import com.tola.payload.response.FareResponse;
import com.tola.service.FareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fares")
public class FareController {

    private final FareService fareService;

    @PostMapping()
    public ResponseEntity<FareResponse> createFare(@Valid @RequestBody FareRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(fareService.createFare(request));
    }

    @GetMapping()
    public ResponseEntity<?> getFares() {
        return ResponseEntity.status(HttpStatus.OK).body(fareService.getFares());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FareResponse> getFareById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(fareService.getFareById(id));
    }

    @GetMapping("/flight/{flightId}/cabin-class/{cabinClassId}")
    public ResponseEntity<List<FareResponse>> getFaresByFlightAndCabinClass(
            @PathVariable Long flightId,
            @PathVariable Long cabinClassId) {
        return ResponseEntity.ok(fareService.getFaresByFlightIdAndCabinClassId(flightId, cabinClassId));
    }

    @PostMapping("/batch-by-ids")
    public ResponseEntity<Map<Long, FareResponse>> getFareIds(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(fareService.getFaresByIds(ids));
    }

    @PostMapping("/search")
    public ResponseEntity<Map<Long, FareResponse>> getLowestFarePerFlight(
            @RequestParam List<Long> flightIds,
            @RequestParam Long cabinClassId
    ) {
        Map<Long, FareResponse> res = fareService.getLowestFarePerFlight(flightIds, cabinClassId);
        System.out.println("search fare response ------" + res);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareResponse> updateFare(
            @PathVariable Long id,
            @Valid @RequestBody FareRequest request
    ) throws Exception {
        return ResponseEntity.ok(fareService.updateFare(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFare(@PathVariable Long id) throws Exception {
        fareService.deleteFare(id);
        ApiResponse response = new ApiResponse("Delete Fare Successfully");
        return ResponseEntity.ok(response);
    }
}
