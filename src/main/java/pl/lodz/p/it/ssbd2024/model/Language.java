package pl.lodz.p.it.ssbd2024.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    PL("pl"), EN("en");
    private final String value;
}
