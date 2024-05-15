package pl.lodz.p.it.ssbd2024.mok.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleOAuth2TokenPayload {
    String iss;
    String azp;
    String aud;
    String sub;
    String email;
    @JsonProperty("email_verified")
    boolean emailVerified;
    @JsonProperty("at_hash")
    String atHash;
    String name;
    String picture;
    @JsonProperty("given_name")
    String givenName;
    @JsonProperty("family_name")
    String familyName;
    int iat;
    int exp;
}