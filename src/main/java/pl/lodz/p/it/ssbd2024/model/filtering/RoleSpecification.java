package pl.lodz.p.it.ssbd2024.model.filtering;

import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.User;

import java.util.Objects;

@Slf4j
public class RoleSpecification<T> extends SpecificationImpl<T> {

    public RoleSpecification(SearchCriteria searchCriteria) {
        super(searchCriteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        return switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS ->  cb.like(cb.lower(userJoin(root).get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case DOES_NOT_CONTAIN -> cb.notLike(cb.lower(userJoin(root).get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case BEGINS_WITH -> cb.like(cb.lower(userJoin(root).get(searchCriteria.getFilterKey())), strToSearch + "%");
            case DOES_NOT_BEGIN_WITH -> cb.notLike(cb.lower(userJoin(root).get(searchCriteria.getFilterKey())), strToSearch + "%");
            case ENDS_WITH -> cb.like(cb.lower(userJoin(root).get(searchCriteria.getFilterKey())), "%" + strToSearch);
            case DOES_NOT_END_WITH -> cb.notLike(cb.lower(userJoin(root).get(searchCriteria.getFilterKey())), "%" + strToSearch);
            case EQUAL -> {
                if (searchCriteria.getFilterKey().equals("active")) {
                    yield  cb.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());
                }
                yield  cb.equal(userJoin(root).get(searchCriteria.getFilterKey()), searchCriteria.getValue());
            }
            case NOT_EQUAL -> cb.notEqual(userJoin(root).get(searchCriteria.getFilterKey()), searchCriteria.getValue());
            case NUL -> cb.isNull(userJoin(root).get(searchCriteria.getFilterKey()));
            case NOT_NULL -> cb.isNotNull(userJoin(root).get(searchCriteria.getFilterKey()));
            case GREATER_THAN -> cb.greaterThan(userJoin(root).<String> get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            case GREATER_THAN_EQUAL -> cb.greaterThanOrEqualTo(userJoin(root).<String> get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            case LESS_THAN -> cb.lessThan(userJoin(root).get(searchCriteria.<String> getFilterKey()), searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL -> cb.lessThanOrEqualTo(userJoin(root).<String> get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            default -> null;
        };
    }

    private Join<Tenant, User> userJoin(Root<T> root) {
        return root.join("user");
    }
}
