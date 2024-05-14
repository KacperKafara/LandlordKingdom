package pl.lodz.p.it.ssbd2024.model.filtering.builders;

import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.filtering.SearchCriteria;
import pl.lodz.p.it.ssbd2024.model.filtering.SearchOperation;
import pl.lodz.p.it.ssbd2024.model.filtering.SpecificationBuilder;
import pl.lodz.p.it.ssbd2024.model.filtering.SpecificationImpl;

import java.util.ArrayList;
import java.util.List;

public class UserSpecificationBuilder implements SpecificationBuilder<User> {
    private final List<SearchCriteria> params;

    public UserSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final UserSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final UserSpecificationBuilder with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<User> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<User> result = new SpecificationImpl<>(params.getFirst());
        for (int i = 1; i < params.size(); i++) {
            SearchCriteria criteria = params.get(i);
            result = SearchOperation.getDataOption(criteria.getDataOption()).equals(SearchOperation.ALL) ?
                    Specification.where(result).and(new SpecificationImpl<>(criteria)) :
                    Specification.where(result).or(new SpecificationImpl<>(criteria));
        }

        return result;
    }
}
