package com.metaltravelguide.places.services.implementation;

import com.metaltravelguide.places.enums.Country;
import com.metaltravelguide.places.exceptions.AlreadyExistsException;
import com.metaltravelguide.places.exceptions.CannotChangeOtherAdminException;
import com.metaltravelguide.places.exceptions.ElementNotFoundException;
import com.metaltravelguide.places.exceptions.UserNotTheSameException;
import com.metaltravelguide.places.mappers.UserMapper;
import com.metaltravelguide.places.models.dtos.UserDTO;
import com.metaltravelguide.places.models.entities.User;
import com.metaltravelguide.places.models.forms.UserCreateForm;
import com.metaltravelguide.places.models.forms.UserUpdateForm;
import com.metaltravelguide.places.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public CustomUserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Connection not possible."));
    }

    public void create(UserCreateForm form) {
        try {
            User user = new User(form.getUsername(), encoder.encode(form.getPassword()));
            userRepository.save(user);
        }
        catch(Exception exception) {
            throw new AlreadyExistsException(form.getUsername(), "username");
        }
    }

    public List<UserDTO> readAll(String role) {
        return userRepository.findUsersByRole(role).stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO readOne(Long id) {
        return UserMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Connection not possible.")));
    }

    public UserDTO update(Long id, UserUpdateForm form) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(User.class, id));
        boolean isAdmin = user.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin)
            throw new CannotChangeOtherAdminException(User.class, user.getUsername());
        if (form.getPassword() != null)
            user.setPassword(encoder.encode(form.getPassword()));
        if (form.getNickname() != null)
            user.setNickname(form.getNickname());
        if (form.getImage() != null)
            user.setImage(form.getImage());
        if (form.getCountryIso() != null)
            user.setCountryIso(findByName(form.getCountryIso()));
        try {
            userRepository.save(user);
        }
        catch(Exception exception) {
            throw new AlreadyExistsException(form.getNickname(), "nickname");
        }
        return UserMapper.toDto(user);
    }

    public UserDTO readProfile(String username) {
        return UserMapper.toDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Connection not possible.")));
    }

    public UserDTO updateProfile(UserUpdateForm form) {
        User user = userRepository.findByUsername(form.getUsername())
                .orElseThrow(() -> new ElementNotFoundException(User.class, form.getUsername()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = user.getUsername().equals(authentication.getName());
        if (!isAdmin && !isOwner)
            throw new UserNotTheSameException(user.getUsername(), authentication.getName());
        if (form.getPassword() != null)
            user.setPassword(encoder.encode(form.getPassword()));
        if (form.getNickname() != null && !form.getNickname().equals(user.getNickname()))
            user.setNickname(form.getNickname());
        if (form.getImage() != null)
            user.setImage(form.getImage());
        if (form.getCountryIso() != null)
            user.setCountryIso(findByName(form.getCountryIso()));
        try {
            userRepository.save(user);
        }
        catch(Exception exception) {
            throw new AlreadyExistsException(form.getNickname(), "nickname");
        }
        return UserMapper.toDto(user);
    }

    public void enable(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(User.class, id));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void disable(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(User.class, id));
        user.setEnabled(false);
        userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(User.class, id));
        boolean isAdmin = user.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin)
            throw new CannotChangeOtherAdminException(User.class, user.getUsername());
        userRepository.delete(user);
    }

    private Country findByName(String countryIso) {
        return Arrays.stream(Country.values()).filter(country -> country.name().equalsIgnoreCase(countryIso)).findFirst().orElse(null);
    }
}