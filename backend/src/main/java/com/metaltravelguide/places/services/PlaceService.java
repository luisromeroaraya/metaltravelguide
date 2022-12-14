package com.metaltravelguide.places.services;

import com.metaltravelguide.places.models.dtos.PlaceDTO;
import com.metaltravelguide.places.models.forms.PlaceCreateForm;
import com.metaltravelguide.places.models.forms.PlaceUpdateForm;

import java.util.List;

public interface PlaceService extends CrudService<PlaceDTO, Long, PlaceCreateForm, PlaceUpdateForm> {
    List<PlaceDTO> readAllFromUser(Long id);
    void addLike(Long id, String username);
    void unLike(Long id, String username);
    void approve(Long id);
    void refuse(Long id);
}
