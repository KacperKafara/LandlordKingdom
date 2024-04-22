package pl.lodz.p.it.ssbd2024.mok.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

import java.util.UUID;

@RestController()
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/block")
    public ResponseEntity<String> blockUser(@RequestBody UUID id) {
        userService.blockUser(id);

        return ResponseEntity.status(HttpStatus.OK).body("User blocked");
    }

    @PostMapping("/unblock")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> unblockUser(@RequestBody UUID id) {
        userService.unblockUser(id);

        return ResponseEntity.status(HttpStatus.OK).body("User unblocked");
    }
}
