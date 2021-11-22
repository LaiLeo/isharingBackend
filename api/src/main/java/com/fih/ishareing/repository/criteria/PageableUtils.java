package com.fih.ishareing.repository.criteria;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fih.ishareing.controller.base.error.exception.ParameterException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class PageableUtils {

	private static final int PAGE_MAX_LIMIT = 65535;

	public static Pageable wrapPageable(Map<String, String> map, Pageable pageable) {
		return wrapPageable(map, pageable, PAGE_MAX_LIMIT);
	}

	public static Pageable wrapPageable(Map<String, String> map, Pageable pageable, int limit) {
		int pageNumber = 0;
		int pageSize = limit;
		Sort sort = null;
		if (null != pageable) {

			if (pageable.getPageSize() > limit) {
				throw new ParameterException("Page size (" + pageable.getPageSize() + ") over the limit : " + limit
						+ ". You can add size=<integer number> in query string to change default value.");
			}

			if (null != pageable.getSort()) {
				List<Order> orders = StreamSupport.stream(pageable.getSort().spliterator(), false).map(o -> {
					String column = o.getProperty();
					String value = map.get(column);
					if (null == value) {
						throw new ParameterException("Sort column not found : " + column + ", Accepted Columns in ["
								+ map.keySet().stream().collect(Collectors.joining(",")) + "]");
					}
					Order order = new Order(o.getDirection(), value);
					return order;
				}).collect(Collectors.toList());
				sort = Sort.by(orders);
			}
			pageNumber = pageable.getPageNumber();
			pageSize = pageable.getPageSize();
		}

		return PageRequest.of(pageNumber, pageSize, sort);
	}
}
