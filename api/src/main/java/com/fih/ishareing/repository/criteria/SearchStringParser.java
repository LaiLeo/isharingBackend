package com.fih.ishareing.repository.criteria;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fih.ishareing.controller.base.error.exception.ParameterException;

import org.springframework.data.jpa.domain.Specification;;

public class SearchStringParser {

	// private static Logger logger =	 LoggerFactory.getLogger(SearchStringParser.class);

	public static <T> Specification<T> getSpecification(Map<String, String> searchColumnMap, String search,
			Class<T> entityClass) {
		SpecificationsBuilder<T> builder = new SpecificationsBuilder<T>(entityClass);
		if (null == search) {
			return null;
		}
		if (null != search) {
			final String REPLACE_STR = "__WHITESAPCE__";
			Pattern pattern1 = Pattern.compile("'(.*?)'");
			Matcher matcher1 = pattern1.matcher(search);
			while (matcher1.find()) {
				String str = matcher1.group(1);
				search = search.replace(str, str.trim().replace(" ", REPLACE_STR));
			}

			Pattern pattern = Pattern.compile(
					"\\s?(and|AND|or|OR)\\s+([\\w.]+?)\\s+(eq|EQ|ne|NE|in|IN|nin|NIN|lt|LT|lte|LTE|gt|GT|gte|GTE|like|LIKE|between|BETWEEN)\\s+(.*?)\\s+?");
			Matcher matcher = pattern.matcher("and " + search + " ");

			while (matcher.find()) {
				//  logger.info("Operator :" + matcher.group(1));
				//  logger.info("Column :" + matcher.group(2));
				//  logger.info("Compare Operator :" + matcher.group(3));

				String jpaColumn = searchColumnMap.get(matcher.group(2));
				if (null == jpaColumn) {
					throw new ParameterException(matcher.group(2) + " is invalid column.");
				}

				String value = matcher.group(4).replaceAll(REPLACE_STR, " ").replaceFirst("'", "");
				int lastIndex = value.length() - 1;
				if (lastIndex == value.lastIndexOf("'")) {
					value = value.substring(0, lastIndex);
				}
				if (matcher.group(3) != null && matcher.group(3).equalsIgnoreCase("like")) {
					value = value.replaceAll("_", "\\\\_");
				}
				// logger.info("Value :" + value);
				builder.with(matcher.group(1), jpaColumn, matcher.group(3), value);
			}
		}

		return builder.build();
	}

}
