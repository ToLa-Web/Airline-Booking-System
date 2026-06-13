package com.tola.location_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tola.embeddable.Address;
import com.tola.embeddable.GeoCode;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true,  length = 3)
    private String iataCode;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;

    @Column(name = "time_zone_id", length = 50)
    private String timeZone;

    @ManyToOne
    @JsonIgnore
    private City city;

    @JsonIgnore
    public String getDetailName() {
        if (city != null && city.getCityCode() != null) {
            return name.toUpperCase() + "/" + city.getCityCode();
        }
        return name.toUpperCase();
    }

}
