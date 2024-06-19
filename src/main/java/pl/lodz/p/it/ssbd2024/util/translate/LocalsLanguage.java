package pl.lodz.p.it.ssbd2024.util.translate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.lodz.p.it.ssbd2024.model.Local;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalsLanguage {
    private List<Local> locals;
    private String language;
}
