package com.metaltravelguide.places.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link com.metaltravelguide.places.models.entities.User} entity
 */
@Data
@Builder
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 459295051233379343L;
    private Long id;
    private String username;
    private String nickname;
    private String image;
    private String countryIso;
    private boolean enabled;
    private List<String> roles;
    private Set<Long> places;
}
