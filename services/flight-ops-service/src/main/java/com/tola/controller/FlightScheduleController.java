package com.tola.controller;

import com.tola.payload.request.FlightScheduleRequest;
import com.tola.payload.response.ApiResponse;
import com.tola.payload.response.FlightScheduleResponse;
import com.tola.service.FlightScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-schedules")
public class FlightScheduleController {

    private final FlightScheduleService flightScheduleService;

    @PostMapping()
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader("X-airline-Id") Long airlineId,
            @Valid @RequestBody FlightScheduleRequest request
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightScheduleService
                .createFlightSchedule(airlineId, request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleById(id));
    }

    @GetMapping()
    public ResponseEntity<?> getFlightSchedules(
            @RequestHeader("X-airline-Id") Long airlineId) throws Exception {
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleByAirline(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long id,
            @RequestBody FlightScheduleRequest request
    ) throws Exception {
        return ResponseEntity.ok(flightScheduleService.updateFlightSchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightSchedule(
            @PathVariable Long id
    ) throws Exception {
        flightScheduleService.deleteFlightSchedule(id);
        ApiResponse apiResponse = new ApiResponse("Schedule deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
