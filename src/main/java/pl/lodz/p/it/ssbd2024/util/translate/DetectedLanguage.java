package pl.lodz.p.it.ssbd2024.util.translate;

import lombok.Data;

@Data
public class DetectedLanguage {
    private String language;
    private double score;
}
