package com.metaltravelguide.places.services.implementation;

import com.metaltravelguide.places.exceptions.ElementNotFoundException;
import com.metaltravelguide.places.exceptions.UserNotTheSameException;
import com.metaltravelguide.places.mappers.CommentMapper;
import com.metaltravelguide.places.models.dtos.CommentDTO;
import com.metaltravelguide.places.models.entities.Comment;
import com.metaltravelguide.places.models.entities.Place;
import com.metaltravelguide.places.models.forms.CommentCreateForm;
import com.metaltravelguide.places.models.forms.CommentUpdateForm;
import com.metaltravelguide.places.repositories.CommentRepository;
import com.metaltravelguide.places.repositories.PlaceRepository;
import com.metaltravelguide.places.repositories.UserRepository;
import com.metaltravelguide.places.services.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PlaceRepository placeRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
    }

    @Override
    public CommentDTO create(CommentCreateForm commentCreateForm) {
        Comment comment = new Comment();
        comment.setText(commentCreateForm.getText());
        comment.setUser(userRepository.findByUsername(commentCreateForm.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username not found.")));
        comment.setPlace(placeRepository.findById(commentCreateForm.getPlaceId()).orElseThrow(() -> new ElementNotFoundException(Place.class, "Place not found.")));
        comment = commentRepository.save(comment);
        return CommentMapper.toDto(comment);
    }

    @Override
    public List<CommentDTO> readAll() {
        return commentRepository.findAll().stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO readOne(Long id) {
        return commentRepository.findById(id)
                .map(CommentMapper::toDto)
                .orElseThrow(() -> new ElementNotFoundException(Comment.class, id));
    }

    @Override
    public List<CommentDTO> readAllByPlace(Long id) {
        return commentRepository.findByPlace_Id(id).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO update(Long id, CommentUpdateForm commentUpdateForm) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(Comment.class, id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = comment.getUser().getUsername().equals(authentication.getName());
        if (!isAdmin && !isOwner)
            throw new UserNotTheSameException(comment.getUser().getUsername(), authentication.getName());
        if (commentUpdateForm.getText() != null)
            comment.setText(commentUpdateForm.getText());
        if (commentUpdateForm.isStatus())
            comment.setStatus(true);
        comment = commentRepository.save(comment);
        return CommentMapper.toDto(comment);
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(Comment.class, id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = comment.getUser().getUsername().equals(authentication.getName());
        if (!isAdmin && !isOwner)
            throw new UserNotTheSameException(comment.getUser().getUsername(), authentication.getName());
        commentRepository.delete(comment);
    }
}