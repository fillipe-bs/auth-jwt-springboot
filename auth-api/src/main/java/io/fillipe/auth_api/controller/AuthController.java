package io.fillipe.auth_api.controller;

import io.fillipe.auth_api.dto.request.LoginRequestDTO;
import io.fillipe.auth_api.dto.request.RegisterRequestDTO;
import io.fillipe.auth_api.dto.request.UpdatePasswordRequestDTO;
import io.fillipe.auth_api.dto.response.AuthResponseDTO;
import io.fillipe.auth_api.dto.response.UserResponseDTO;
import io.fillipe.auth_api.model.User;
import io.fillipe.auth_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO dto){

        User user = new User();

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        userService.register(user);

        AuthResponseDTO response = new AuthResponseDTO("User created successfully");

        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO dto){

        String email = dto.getEmail();
        String password = dto.getPassword();

        User user = userService.authenticate(email, password);

        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<AuthResponseDTO> updatePassword(
            @RequestBody UpdatePasswordRequestDTO dto,
            @RequestParam String email) {

        User user = userService.getByEmail(email);

        userService.updatePassword(user, dto.getPassword(), dto.getNewPassword());

        AuthResponseDTO response = new AuthResponseDTO("Password updated successfully");
        return ResponseEntity.ok(response);
    }

}
