package pl.lodz.p.it.ssbd2024.model.filtering.builders;

import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.ssbd2024.exceptions.InvalidDataException;
import pl.lodz.p.it.ssbd2024.messages.FilterMessages;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.filtering.RoleSpecification;
import pl.lodz.p.it.ssbd2024.model.filtering.SearchCriteria;
import pl.lodz.p.it.ssbd2024.model.filtering.SearchOperation;
import pl.lodz.p.it.ssbd2024.model.filtering.SpecificationBuilder;

import java.util.ArrayList;
import java.util.List;

public class TenantSpecificationBuilder implements SpecificationBuilder<Tenant> {
    private final List<SearchCriteria> params;

    public TenantSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final TenantSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final TenantSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<Tenant> build() throws InvalidDataException {
        try {
            if (params.isEmpty()) {
                return null;
            }

            Specification<Tenant> result = new RoleSpecification<>(params.getFirst());
            for (int i = 1; i < params.size(); i++) {
                SearchCriteria criteria = params.get(i);
                result = SearchOperation.getDataOption(criteria.getDataOption()).equals(SearchOperation.ALL) ?
                        Specification.where(result).and(new RoleSpecification<>(criteria)) :
                        Specification.where(result).or(new RoleSpecification<>(criteria));
            }

            return result;
        } catch (Exception e) {
            throw new InvalidDataException(FilterMessages.INVALID_DATA);
        }
    }
}
