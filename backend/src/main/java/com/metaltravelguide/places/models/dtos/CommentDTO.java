package com.metaltravelguide.places.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.metaltravelguide.places.models.entities.Comment} entity
 */
@Data
@Builder
public class CommentDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4407513850995464811L;
    private Long id;
    private String text;
    private boolean status;
    private Instant dateCreated;
    private Long userId;
    private String userNickname;
    private Long placeId;
}