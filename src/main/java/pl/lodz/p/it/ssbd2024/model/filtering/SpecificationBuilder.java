package pl.lodz.p.it.ssbd2024.model.filtering;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public interface SpecificationBuilder<T> {
    SpecificationBuilder<T> with(String key, String operation, Object value);

    SpecificationBuilder<T> with(SearchCriteria searchCriteria);

    Specification<T> build();
}
