package com.fih.ishareing.repository.criteria;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FusionSpecificationsBuilder<T> {
	private final List<SearchCriteria> params;
	private List<Specification<T>> additionalConditions;
	private Class<T> entityClass;
	
    public FusionSpecificationsBuilder(Class<T> entityClass) {
        params = new ArrayList<SearchCriteria>();
        this.entityClass = entityClass;
    }
 
    public FusionSpecificationsBuilder<T> with(String orPredicate, String key, String operation, Object value) {
        params.add(new SearchCriteria(orPredicate, key, operation, value));
        return this;
    }
 
	public FusionSpecificationsBuilder<T> with(String orPredicate, String key, String operation, Object value,
			boolean replaceIfPresent) {

		boolean exist = params.stream().anyMatch(p -> p.getKey().equalsIgnoreCase(key));
		if (exist) {
			if (replaceIfPresent) {
				params.removeIf(f -> f.getKey().equalsIgnoreCase(key));
				params.add(new SearchCriteria(orPredicate, key, operation, value));
			}
		} else {
			params.add(new SearchCriteria(orPredicate, key, operation, value));
		}

		return this;
	}

    public Specification<T> build() {
        if (params.size() == 0) {
            return null;
        }
 
//        List<Specification<T>> specs = new ArrayList<Specification<T>>();
//        for (SearchCriteria param : params) {
//            specs.add(new FusionSpecification(param, entityClass));
//        }
 
      	CriteriaFilterCondition filter = new CriteriaFilterCondition();
		Specification<T> result = Specification.where(new FusionSpecification<T>(params.get(0), entityClass, filter));
        
        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
              ? result.or(new FusionSpecification<T>(params.get(i), entityClass, filter)) 
              : result.and(new FusionSpecification<T>(params.get(i), entityClass, filter));
        }
        return result;
    }

	public List<SearchCriteria> getParams() {
		return params;
	}
    
	public void addAdditionalConditions(Specification<T> spec) {
		if (additionalConditions == null) {
			additionalConditions = new ArrayList<Specification<T>>();
		}
		additionalConditions.add(spec);
	}
	
	public Specification<T> buildWithAdditionConditions() {
		if (CollectionUtils.isEmpty(additionalConditions) && params.size() == 0) return null;
		if (params.size() != 0) {
			CriteriaFilterCondition filter = new CriteriaFilterCondition();
			Specification<T> result = Specification.where(new FusionSpecification<T>(params.get(0), entityClass, filter));
	        for (int i = 1; i < params.size(); i++) {
	            result = params.get(i).isOrPredicate()
	              ? result.or(new FusionSpecification<T>(params.get(i), entityClass, filter)) 
	              : result.and(new FusionSpecification<T>(params.get(i), entityClass, filter));
	        }
	        if (CollectionUtils.isNotEmpty(additionalConditions)) {
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

	public Class<T> getEntityClass() {
		return entityClass;
	}
    
}
