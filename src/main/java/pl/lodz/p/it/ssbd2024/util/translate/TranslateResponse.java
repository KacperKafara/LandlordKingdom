package pl.lodz.p.it.ssbd2024.util.translate;

import lombok.Data;

@Data
public class TranslateResponse {
    private String translatedText;
    private DetectedLanguage detectedLanguage;
}
