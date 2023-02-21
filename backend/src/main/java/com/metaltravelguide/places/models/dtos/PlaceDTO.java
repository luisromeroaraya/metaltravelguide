package com.metaltravelguide.places.models.dtos;

import com.metaltravelguide.places.models.entities.Address;
import com.metaltravelguide.places.models.entities.Contact;
import com.metaltravelguide.places.enums.Type;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * A DTO for the {@link com.metaltravelguide.places.models.entities.Place} entity
 */
@Data
@Builder
public class PlaceDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8710012142831720952L;
    private Long id;
    private String googleId;
    private String name;
    private Address address;
    private Contact contact;
    private Type type;
    private String description;
    private String image;
    private Long userId;
    private String userNickname;
    private Set<String> likes;
    private boolean status;
    private Instant dateCreated;
    private Instant dateLastModified;
}
