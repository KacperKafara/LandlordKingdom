package pl.lodz.p.it.ssb2024.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.ssb2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssb2024.mok.dto.AuthenticationResponse;
import pl.lodz.p.it.ssb2024.mok.services.impl.AuthenticationServiceImpl;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request.getLogin(), request.getPassword()));
    }
}
