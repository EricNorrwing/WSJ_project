package com.ea.wsj_project.service;

import com.ea.wsj_project.model.User;
import com.ea.wsj_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(Long id) {
        try {
            return userRepository.findById(id);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> createUser(User user) {
        userRepository.save(user);
        return userRepository.findByUsername(user.getUsername());
    }

    @Override
    public Optional<User> updateUser(User user) {
        if (user == null || user.getId() == null) {
            return Optional.empty();
        }

        try {

        userRepository.save(user);
        return userRepository.findByUsername(user.getUsername());

        } catch (IllegalArgumentException e) {
            logger.error("Failed to update user: {}", user.getUsername(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> deleteUser(Long id) {
        if (id == null) {
            logger.error("Failed to delete user because the id is null");
            return Optional.empty();
        }

        try {

            Optional<User> userOptional = userRepository.findById(id);

            if (userOptional.isEmpty()) {
                logger.warn("User with ID {} not found, cannot delete.", id);
                return Optional.empty();
            }

            userRepository.deleteById(id);
            logger.info("User with ID {} has been deleted successfully.", id);

            return userOptional;
        } catch (IllegalArgumentException e) {
            logger.error("An error occurred while deleting the user with ID {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }
}
