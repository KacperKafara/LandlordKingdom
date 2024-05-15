package pl.lodz.p.it.ssbd2024.mok.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleOAuth2TokenResponse {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("expires_in")
    int expiresIn;
    @JsonProperty("refresh_token")
    String refreshToken;
    String scope;
    @JsonProperty("token_type")
    String tokenType;
    @JsonProperty("id_token")
    String idToken;
}
