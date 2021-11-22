package com.fih.ishareing.utils;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DBHelper {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;

//	@Autowired
//	@Qualifier("primaryJdbcTemplate")
//	protected JdbcTemplate jdbcTemplate;

	private static Logger logger = LoggerFactory.getLogger("DBHelper");

	public List<Map<String, Object>> Query(String sql, Object[] parameters) {
		try {
			List<Map<String, Object>> results = (List<Map<String, Object>>) jdbcTemplate.queryForList(sql, parameters);
			return results;
		} catch (Exception e) {
			logger.error("DBException:" + e.getMessage());
			return null;
		}
	}

	public List<String> QueryListString(String sql) {
		try {
			List<String> results = (List<String>) jdbcTemplate.queryForList(sql, String.class);
			return results;
		} catch (Exception e) {
			logger.error("DBException:" + e.getMessage());
			return null;
		}
	}

	public String QueryString(String sql, Object[] parameters) {
		try {
			String results = (String) jdbcTemplate.queryForObject(sql, parameters, String.class);
			return results;
		} catch (Exception e) {
			logger.error("DBException:" + e.getMessage());
			return null;
		}
	}
	
	public Long QueryLong(String sql, Object[] parameters) {
		try {
			Long results = (Long) jdbcTemplate.queryForObject(sql, parameters, Long.class);
			return results;
		} catch (Exception e) {
			logger.error("DBException:" + e.getMessage());
			return null;
		}
	}

	public boolean InsertorUpdate(String sql, Object[] parameters) {
		try {
			int res = jdbcTemplate.update(sql, parameters);
			if (res > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("DBException:" + e.getMessage());
			return false;
		}
	}

	public <T> List<T> Query(Class<T> t, String sql, Object[] args) {
		try {
			List<T> userList = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<T>(t));
			return userList;
		} catch (Exception e) {
			logger.error("DBException:" + e.getMessage());
		}
		return null;
	}

	public Integer Count(String sql, Object[] parameters) {
		try {
			Integer res = jdbcTemplate.queryForObject(sql, parameters, Integer.class);

			return res;
		} catch (Exception e) {
			logger.error("DBException:" + e.getMessage());
			return 0;
		}
	}
}
