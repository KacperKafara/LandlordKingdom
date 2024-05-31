package pl.lodz.p.it.ssbd2024.mok.dto;

import java.util.StringJoiner;

public record PasswordHolder(
        String password
) {
    @Override
    public String toString() {
        return new StringJoiner(", ", PasswordHolder.class.getSimpleName() + "[", "]")
                .add("password='*********'")
                .toString();
    }
}
