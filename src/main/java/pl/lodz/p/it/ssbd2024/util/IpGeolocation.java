package pl.lodz.p.it.ssbd2024.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IpGeolocation {
    private String ip;
    private String hostname;
    private String city;
    private String region;
    private String country;
    private String loc;
    private String org;
    private String postal;
    private String timezone;
}
