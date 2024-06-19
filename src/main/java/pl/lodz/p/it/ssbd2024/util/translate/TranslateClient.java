package pl.lodz.p.it.ssbd2024.util.translate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class TranslateClient {
    public String translate(TranslateRequest request) {

        RestClient webClient = RestClient.create("http://51.20.12.73:5555/translate");

        TranslateResponse res = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(TranslateResponse.class);

        return res != null ? res.getTranslatedText() : request.q();
    }
}
