package com.tola.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityRequest {
    @NotBlank(message = "City name is required")
    @Size(max = 100, message = "City name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "City code is required")
    @Size(max = 10, message = "City code must not exceed 10 characters")
    private String cityCode;

    @NotBlank(message = "Country code is required")
    @Size(max = 5, message = "Country code must not exceed 10 characters")
    private String countryCode;

    @NotBlank(message = "Country name is required")
    @Size(max = 100, message = "Country name must not exceed 100 characters")
    private String countryName;

    @Size(max = 10)
    private String regionCode;

    @Size(max = 10)
    private String timeZoneOffset;

}
