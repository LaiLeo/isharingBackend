package com.fih.ishareing.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.util.StringUtils;

@Configuration
@PropertySources({ @PropertySource(value = "file:./application-prod.properties", ignoreResourceNotFound = true) })
public class DefaultValueConfig {
	
	
	private Boolean columnActive;
	
	
	private Integer pageSize;

	public Boolean getColumnActive() {
		return columnActive;
	}

	@Value("${default.column.active}")
	public void setColumnActive(String columnActive) {
		if (!StringUtils.isEmpty(columnActive)) {
			this.columnActive = Boolean.valueOf(columnActive.toLowerCase());
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	@Value("${default.page.size}")
	public void setPageSize(String pageSize) {
		if (!StringUtils.isEmpty(pageSize)) {
			this.pageSize = Integer.valueOf(pageSize);
		}
	}
	
}
