package com.fih.ishareing.repository.criteria;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

public class SpecificationsBuilder<T> {
	private final List<SearchCriteria> searchCriterias;
	private List<Specification<T>> additionalConditions;
	private Class<T> entityClass;

	public SpecificationsBuilder(Class<T> entityClass) {
		searchCriterias = new ArrayList<SearchCriteria>();
		this.entityClass = entityClass;
	}

	public SpecificationsBuilder<T> with(String orPredicate, String key, String operation, Object value) {
		try {
			searchCriterias.add(new SearchCriteria(orPredicate, key, operation, value));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public Specification<T> build() {
		if (searchCriterias.size() == 0) {
			return null;
		}

		// List<Specification<T>> specs = new ArrayList<Specification<T>>();
		// for (SearchCriteria param : params) {
		// specs.add(new FusionSpecification(param, entityClass));
		// }

		CriteriaFilterCondition filter = new CriteriaFilterCondition();
		Specification<T> result = Specification.where(new SearchSpecification<T>(searchCriterias.get(0), entityClass, filter));

		for (int i = 1; i < searchCriterias.size(); i++) {
			result = searchCriterias.get(i).isOrPredicate() ?
					result.or(new SearchSpecification<T>(searchCriterias.get(i), entityClass, filter)) : 
						result.and(new SearchSpecification<T>(searchCriterias.get(i), entityClass, filter));
		}

		return result;
	}

	public List<SearchCriteria> getSearchCriterias() {
		return searchCriterias;
	}

	public void addAdditionalConditions(Specification<T> spec) {
		if (additionalConditions == null) {
			additionalConditions = new ArrayList<Specification<T>>();
		}
		additionalConditions.add(spec);
	}

	public Specification<T> buildWithAdditionConditions() {
		if (CollectionUtils.isEmpty(additionalConditions) && searchCriterias.size() == 0)
			return null;
		if (searchCriterias.size() != 0) {
			CriteriaFilterCondition filter = new CriteriaFilterCondition();
			Specification<T> result = Specification.where(new SearchSpecification<T>(searchCriterias.get(0), entityClass, filter));
			for (int i = 1; i < searchCriterias.size(); i++) {
				result = searchCriterias.get(i).isOrPredicate() ? result.or(new SearchSpecification<T>(searchCriterias.get(i), entityClass, filter)) : result.and(new SearchSpecification<T>(searchCriterias.get(i), entityClass, filter));
			}
			if (!CollectionUtils.isEmpty(additionalConditions)) {
				for (Specification<T> spec : additionalConditions) {
					result.and(spec);
				}
			}
			return result;
		} else {
			Specification<T> result = Specification.where(additionalConditions.get(0));
			for (int i = 1; i < additionalConditions.size(); i++) {
				result.and(additionalConditions.get(i));
			}
			return result;
		}
	}
}
