package com.tola.location_service.service;

import com.tola.payload.request.CityRequest;
import com.tola.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    CityResponse createCity(CityRequest cityResponse) throws Exception;
    CityResponse getCityById(Long id) throws Exception;

    CityResponse updateCity(Long id, CityRequest cityRequest) throws Exception;
    void deleteCity(Long id) throws Exception;
    Page<CityResponse> getAllCities(Pageable pageable);
    Page<CityResponse> searchCities(String keyword, Pageable pageable);
    Page<CityResponse> searchCitiesByCountryCode(String countryCode, Pageable pageable);

    boolean cityExists(String cityCode);
}
