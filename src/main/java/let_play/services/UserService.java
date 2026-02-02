package let_play.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import let_play.dtos.request.UpdateUserRequest;
import let_play.dtos.response.UserResponse;
import let_play.entities.User;
import let_play.exceptions.DuplicatedException;
import let_play.exceptions.NotFoundException;
import let_play.repositories.ProductRepository;
import let_play.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    public UserResponse getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        return UserResponse.fromEntity(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponse.fromEntity(user))
                .toList();
    }

    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", "id", id));

        return UserResponse.fromEntity(user);
    }

    public UserResponse updateUser(String userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User", "id", userId));

        if (request.getEmail() != null && !user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicatedException("email already exist");
        }

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return UserResponse.fromEntity(updatedUser);
    }

    @Transactional
    public void deleteUser(String id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", "id", id));

        userRepository.deleteById(id);
        productRepository.deleteByUserId(id);
    }

}
