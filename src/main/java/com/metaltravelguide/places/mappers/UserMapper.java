package com.metaltravelguide.places.mappers;

import com.metaltravelguide.places.models.dtos.UserDTO;
import com.metaltravelguide.places.models.entities.Place;
import com.metaltravelguide.places.models.entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserDTO toDto(User entity) {
        if (entity == null)
            return null;
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .nickname(entity.getNickname())
                .image(entity.getImage())
                .countryIso(entity.getCountryIso().name())
                .enabled(entity.isEnabled())
                .roles(entity.getRoles())
                .places(entity.getPlaces().stream().map(Place::getId).collect(Collectors.toSet()))
                .build();
    }
}