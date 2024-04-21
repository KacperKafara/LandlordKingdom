package pl.lodz.p.it.ssb2024.mok.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssb2024.model.User;
import pl.lodz.p.it.ssb2024.mok.services.impl.JwtService;
import pl.lodz.p.it.ssb2024.mok.services.impl.UserServiceImpl;

import java.util.List;
import java.util.UUID;

@RestController
public class TestController {

    UserServiceImpl userService;

    JwtService jwtService;

    @Autowired
    public TestController(UserServiceImpl service, JwtService jwtService) {
        this.userService = service;
        this.jwtService = jwtService;
    }

    @GetMapping
    public String test() {
        return "Test1";
    }

    @PostMapping(value = "/token",consumes = {"application/json"},  produces = {"text/plain"})
    public ResponseEntity<String > jwt(@RequestBody UUID id) throws Exception {
        User user = userService.getUser(id);


        return ResponseEntity.status(HttpStatus.OK).body(jwtService.generateToken( user.getId(), List.of("ADMINISTRATOR", "USER")));
    }

    @GetMapping("/authorized")
    public ResponseEntity<String> auth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return ResponseEntity.ok(authentication.getAuthorities().toString());
    }


}
