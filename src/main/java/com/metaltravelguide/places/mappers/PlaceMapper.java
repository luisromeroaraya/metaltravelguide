package com.metaltravelguide.places.mappers;

import com.metaltravelguide.places.models.dtos.PlaceDTO;
import com.metaltravelguide.places.models.entities.Place;
import com.metaltravelguide.places.models.entities.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlaceMapper {
    public static PlaceDTO toDto(Place entity) {
        if (entity == null)
            return null;
        return PlaceDTO.builder()
                .id(entity.getId())
                .googleId(entity.getGoogleId())
                .name(entity.getName())
                .address(entity.getAddress())
                .contact(entity.getContact())
                .type(entity.getType())
                .description(entity.getDescription())
                .image(entity.getImage())
                .userId(entity.getUser().getId())
                .userNickname(entity.getUser().getNickname())
                .likes(entity.getLikes().stream().map(User::getUsername).collect(Collectors.toSet()))
                .status(entity.isStatus())
                .dateCreated(entity.getDateCreated())
                .dateLastModified(entity.getDateLastModified())
                .build();
    }
}