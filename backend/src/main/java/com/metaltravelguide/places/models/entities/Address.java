package com.metaltravelguide.places.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 5025774476188403719L;
    @Column(name = "address_street", nullable = false)
    private String street;
    @Column(name = "address_number", nullable = false)
    private String number;
    @Column(name = "address_extra")
    private String extra;
    @Column(name = "address_city", nullable = false)
    private String city;
    @Column(name = "address_region", nullable = false)
    private String region;
    @Column(name = "address_country_iso", nullable = false, columnDefinition = "CHAR(2)")
    private String countryIso;
    @Column(name = "address_lat", nullable = false)
    private double lat;
    @Column(name = "address_lon", nullable = false)
    private double lon;
}
