package let_play.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import let_play.dtos.request.LoginRequest;
import let_play.dtos.request.RegisterRequest;
import let_play.dtos.response.AuthResponse;
import let_play.dtos.response.UserResponse;
import let_play.entities.Role;
import let_play.entities.User;
import let_play.exceptions.BadRequestException;
import let_play.repositories.UserRepository;
import let_play.security.JwtUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("email already exist");
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.USER);

        User savedUser = userRepository.save(newUser);
        String token = jwtUtils.generateToken(savedUser);

        return new AuthResponse(token, UserResponse.fromEntity(savedUser));
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = (User) authentication.getPrincipal();

        String token = jwtUtils.generateToken(user);

        return new AuthResponse(token, UserResponse.fromEntity(user));
    }
}