package com.metaltravelguide.places.mappers;

import com.metaltravelguide.places.models.dtos.CommentDTO;
import com.metaltravelguide.places.models.entities.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public static CommentDTO toDto(Comment entity) {
        if (entity == null)
            return null;
        return CommentDTO.builder()
                .id(entity.getId())
                .text(entity.getText())
                .status(entity.isStatus())
                .dateCreated(entity.getDateCreated())
                .userId(entity.getUser().getId())
                .userNickname(entity.getUser().getNickname())
                .placeId(entity.getPlace().getId())
                .build();
    }
}