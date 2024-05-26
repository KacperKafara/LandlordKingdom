package pl.lodz.p.it.ssbd2024.model.filtering.specifications;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.ssbd2024.model.filtering.SearchCriteria;
import pl.lodz.p.it.ssbd2024.model.filtering.SearchOperation;

import java.util.Objects;

public class BasicSpecification<T> implements Specification<T> {
    private final SearchCriteria searchCriteria;

    public BasicSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        return switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS -> cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case DOES_NOT_CONTAIN -> cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case BEGINS_WITH -> cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch + "%");
            case DOES_NOT_BEGIN_WITH -> cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch + "%");
            case ENDS_WITH -> cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);
            case DOES_NOT_END_WITH -> cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);
            case EQUAL -> cb.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());
            case NOT_EQUAL -> cb.notEqual(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());
            case NUL -> cb.isNull(root.get(searchCriteria.getFilterKey()));
            case NOT_NULL -> cb.isNotNull(root.get(searchCriteria.getFilterKey()));
            case GREATER_THAN -> cb.greaterThan(root.<String> get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            case GREATER_THAN_EQUAL -> cb.greaterThanOrEqualTo(root.<String> get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            case LESS_THAN -> cb.lessThan(root.get(searchCriteria.<String> getFilterKey()), searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL -> cb.lessThanOrEqualTo(root.<String> get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            default -> null;
        };
    }
}