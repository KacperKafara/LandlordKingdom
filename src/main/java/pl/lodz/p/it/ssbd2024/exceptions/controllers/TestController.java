package pl.lodz.p.it.ssbd2024.exceptions.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2024.mok.services.impl.JwtService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

    private final JwtService jwtService;

    private final VerificationTokenService tokenService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/token",consumes = {"application/json"},  produces = {"text/plain"})
    public ResponseEntity<String > jwt(@RequestBody UUID id) throws Exception {
        User user = userService.getUserById(id);


        return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken( user.getId(), List.of("ADMINISTRATOR", "USER")));
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/verification-token",consumes = {"application/json"},  produces = {"text/plain"})
    public ResponseEntity<String > verificationToken(@RequestBody UUID id) throws Exception {
//        User user = userService.getUserById(id);


        return ResponseEntity.status(HttpStatus.OK).body(tokenService.generateAccountVerificationToken(userService.getUserById(id)));
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/verification-token2",consumes = {"application/json"},  produces = {"text/plain"})
    public ResponseEntity<String > verificationToken2(@RequestBody UUID id) throws Exception {
//        User user = userService.getUserById(id);


        return ResponseEntity.status(HttpStatus.OK).body(tokenService.generateEmailVerificationToken(userService.getUserById(id)));
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/verify-token/{token}",  produces = {"text/plain"})
    public ResponseEntity<String > verifyToken(@PathVariable String token) throws Exception {
//        User user = userService.getUserById(id);


        return ResponseEntity.status(HttpStatus.OK).body(tokenService.validateAccountVerificationToken(token).getUser().getLogin());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/authorized")
    public ResponseEntity<String> auth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return ResponseEntity.ok(authentication.getAuthorities().toString());
    }


}
