package com.tola.payload.response;

import com.tola.embeddable.Support;
import com.tola.enums.AirlineStatus;
import com.tola.payload.dto.UserDTO;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponse {

    private Long id;
    private String iataCode;
    private String icaoCode;
    private String name;
    private String alias;

    private String logoUrl;
    private String website;

    private AirlineStatus status;
    private String alliance;

    private Instant createdAt;
    private Instant updatedAt;

    private Long ownerId;
    private UserDTO owner;

    private Long updatedByUserId;
    private CityResponse headquartersCity;

    private Support support;
}
