package pl.lodz.p.it.ssbd2024.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.util.translate.TranslateClient;

@RestController
@RequiredArgsConstructor
public class FallbackController {
    private final TranslateClient translateClient;

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public void fallback() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endpoint or resource not found");
    }

}
