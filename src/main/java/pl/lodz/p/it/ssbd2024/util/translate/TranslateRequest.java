package pl.lodz.p.it.ssbd2024.util.translate;

public record TranslateRequest(
        String q,
        String source,
        String target,
        String format
) {
}
