package pl.lodz.p.it.ssb2024.mok.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.lodz.p.it.ssb2024.model.User;
import pl.lodz.p.it.ssb2024.mok.dto.UserCreateRequest;
import pl.lodz.p.it.ssb2024.mok.services.UserService;

import java.net.URI;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity.BodyBuilder registerUser(@RequestBody UserCreateRequest newUserData) {
        User newUser = new User(
                newUserData.firstName(),
                newUserData.lastName(),
                newUserData.email(),
                newUserData.login()
        );
        userService.registerUser(newUser, newUserData.password());

        URI userLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/auth/")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(userLocation);
    }
}
