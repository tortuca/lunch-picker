package com.tortuca.lunchpicker.service;

import com.tortuca.lunchpicker.model.User;
import com.tortuca.lunchpicker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> createUser(String username) {
        User user = new User(username);
        User found = userRepository.findByUsernameAndActive(username, true);
        if (found != null) {
            return Optional.of(found);
        }
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> getUser(String username) {
        User found = userRepository.findByUsernameAndActive(username, true);
        return found != null
                ? Optional.of(found)
                : Optional.empty();
    }
}
