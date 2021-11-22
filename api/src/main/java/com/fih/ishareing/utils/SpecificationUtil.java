package com.fih.ishareing.utils;


import com.fih.ishareing.errorHandling.exceptions.ParameterException;
import com.fih.ishareing.repository.criteria.FusionSpecificationsBuilder;
import com.fih.ishareing.repository.criteria.SearchCriteria;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SpecificationUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SpecificationUtil.class);

	public <T> Specification<T> getSpecification(Map<String, String> searchColumnMap, String search,
			Class<T> entityClass, List<SearchCriteria> additionalCris) {

		if (null == search && CollectionUtils.isEmpty(additionalCris)) {
			return null;
		}

		if (StringUtils.isBlank(search))
			search = "";

		String[] searchList = search.trim().split(" ");
		String search2 = "";
		for (int i = 0; i < searchList.length; i++) {
			if (!searchList[i].equals("'")) {
				search2 = search2 + " ";
			}
			search2 = search2 + searchList[i].replaceAll(" ", "");
		}

		FusionSpecificationsBuilder<T> builder = getFusionSpecificationsBuilder(searchColumnMap, search2, entityClass);
		if (!CollectionUtils.isEmpty(additionalCris)) {
			additionalCris.stream().forEach(cri -> {
				try {
					builder.with(cri.isOrPredicate() ? "or" : "and", cri.getKey(), cri.getOperation(), cri.getValue(),
							true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

		return builder.build();
	}
	
	/**
	 * <h1>General Search Usage</h1> <br/>
	 * 	回傳 Specification, 可以直接使用在Repository的function()
	 * @return Specification
	 * */
	public <T> Specification<T> getSpecification(Map<String, String> searchColumnMap, String search, Class<T> entityClass) {
		if (null == search) {return null;}
		String[] searchList = search.split(" ");
		String search2 = "";
		for(int i = 0; i < searchList.length; i++) {
			if (!searchList[i].equals("'")) {
				search2 = search2 + " ";
			}
			search2 = search2 + searchList[i].replaceAll(" ", "");
		}
		return getFusionSpecificationsBuilder(searchColumnMap, search2, entityClass).build();
	}
	
	/**
	 * <h1>Custom Search Usage</h1> <br/>
	 * 回傳FusionSpecificationsBuilder, 可以透過with()方法再新增新的查詢條件, 
	 * 主要用在有隱藏條件(不出現在URL上的search參數, 換句話說, 就是不提供給前端查詢的條件), 透過builder的方式除了可以新增額外的條件之外,也可以加入or判斷式 , 
	 * 可以參考TaskRepositoryImpl的寫法
	 * @return FusionSpecificationsBuilder
	 * */
	public <T> FusionSpecificationsBuilder<T> getFusionSpecificationsBuilder(Map<String, String> searchColumnMap,String search, Class<T> entityClass) {
		FusionSpecificationsBuilder<T> builder = new FusionSpecificationsBuilder<T>(entityClass);
		if (null != search) {
			final String REPLACE_STR = "__WHITESAPCE__"; 
			Pattern pattern1 = Pattern.compile("'(.*?)'");
			Matcher matcher1 = pattern1.matcher(search);
			while(matcher1.find()) {
				String str = matcher1.group(1);
				search = search.replace(str, str.trim().replace(" ", REPLACE_STR));
			}
			
			Pattern pattern = Pattern.compile("\\s?(and|AND|or|OR)\\s+([\\w.]+?)\\s+(eq|EQ|ne|NE|in|IN|nin|NIN|lt|LT|lte|LTE|gt|GT|gte|GTE|like|LIKE|between|BETWEEN)\\s+(.*?)\\s+?");
	        Matcher matcher = pattern.matcher("and " + search + " ");
	        
	        while (matcher.find()) {
	            logger.debug("Operator :" + matcher.group(1));
	            logger.debug("Compare Operator :" + matcher.group(3));
	            String column = matcher.group(2);
	            logger.debug("Column :" + column);
	            String jpaColumn = searchColumnMap.get(column);
	            if (null == jpaColumn) {
	            	throw new ParameterException(column + " is invalid column.");
	            }
	            String value = matcher.group(4).replaceAll(REPLACE_STR, " ").replaceFirst("'", "");
	            int lastIndex = value.length() -1;
	            if (lastIndex == value.lastIndexOf("'")) {
	            	value = value.substring(0, lastIndex);
	            }
				if (matcher.group(3) != null && matcher.group(3).equalsIgnoreCase("like")) {
					value = value.replaceAll("_", "\\\\_");
				}
	            logger.debug("Value :" + value);
	            builder.with(matcher.group(1), jpaColumn, matcher.group(3), value);
	        }
		}
        return builder;
	}
	
}
