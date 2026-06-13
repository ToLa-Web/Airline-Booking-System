package com.tola.service;

import com.tola.model.Fare;
import com.tola.payload.request.FareRequest;
import com.tola.payload.response.FareResponse;
import java.util.List;
import java.util.Map;

public interface FareService {
    FareResponse createFare(FareRequest request);
    FareResponse getFareById(Long id);
    List<FareResponse> getFaresByFlightIdAndCabinClassId(Long flightId, Long cabinClassId);
    FareResponse updateFare(Long id, FareRequest request);
    void deleteFare(Long id);
    List<Fare> getFares();

    Map<Long, FareResponse> getLowestFarePerFlight(
            List<Long> flightIds, Long cabinClassId
    );

    Map<Long, FareResponse> getFaresByIds(List<Long> ids);
}
