package com.metaltravelguide.places.controllers;

import com.metaltravelguide.places.models.dtos.PlaceDTO;
import com.metaltravelguide.places.models.forms.PlaceCreateForm;
import com.metaltravelguide.places.models.forms.PlaceUpdateForm;
import com.metaltravelguide.places.services.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://luisromeroaraya.github.io"})
@RestController
@RequestMapping("/api/place")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/all")
    public List<PlaceDTO> readAll() {
        return placeService.readAll();
    }

    @GetMapping("/user/{id:[0-9]+}")
    public List<PlaceDTO> readAllFromUser(@PathVariable Long id) {
        return placeService.readAllFromUser(id);
    }

    @GetMapping("/{id:[0-9]+}")
    public PlaceDTO readOne(@PathVariable Long id) {
        return placeService.readOne(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public PlaceDTO create(@Valid @RequestBody PlaceCreateForm placeCreateForm, Authentication auth) {
        placeCreateForm.setUsername(auth.getName());
        return placeService.create(placeCreateForm);
    }

    @PatchMapping("/update/{id:[0-9]+}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public PlaceDTO update(@Valid @PathVariable Long id, @Valid @RequestBody PlaceUpdateForm placeUpdateForm) {
        return placeService.update(id, placeUpdateForm);
    }

    @PatchMapping("/like/{id:[0-9]+}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void addLike(@Valid @PathVariable Long id, Authentication auth) {
        placeService.addLike(id, auth.getName());
    }

    @PatchMapping("/unlike/{id:[0-9]+}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void unLike(@Valid @PathVariable Long id, Authentication auth) {
        placeService.unLike(id, auth.getName());
    }

    @PatchMapping("/approve/{id:[0-9]+}")
    @Secured({"ROLE_ADMIN"})
    public void approve(@Valid @PathVariable Long id) {
        placeService.approve(id);
    }

    @PatchMapping("/refuse/{id:[0-9]+}")
    @Secured({"ROLE_ADMIN"})
    public void refuse(@Valid @PathVariable Long id) {
        placeService.refuse(id);
    }

    @DeleteMapping("/delete/{id:[0-9]+}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void delete(@Valid @PathVariable Long id) {
        placeService.delete(id);
    }
}
