package com.fih.ishareing.repository.criteria;



import com.fih.ishareing.errorHandling.exceptions.ParameterException;

import java.lang.reflect.Field;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomizedEntry<T> {
	private String columnPath;
	private Class<T> entityClass;
	
	public CustomizedEntry(String columnPath, Class<T> entityClass) {
		this.columnPath = columnPath;
		this.entityClass = entityClass;
	}
	
	public Class<?> getFieldClass() {
		try {
			String[] columns = columnPath.split("\\.");
			Queue<String> queue = new ConcurrentLinkedQueue<String>();
			for (String column : columns) {
				queue.offer(column);
			}
			return recursiveGetClass(queue, entityClass);
		} catch (Exception e) {
			throw new ParameterException(e.getMessage(), e);
		}
	}
	
	public Class<?> recursiveGetClass(Queue<String> queue, Class<?> entityClass) throws Exception {
		Class<?> result = null;
		Class<?> temp = entityClass;
		while(!queue.isEmpty()) {
			String columnName = queue.poll();
			Field field = temp.getDeclaredField(columnName);
			//field.setAccessible(true);
			temp = ReflectionUtil.getGenricTypeOfField(field);
			if (null == temp) { throw new Exception(String.format("parse customized entry error.column path : %s, column name : %s", columnPath, columnName));}
			if (queue.isEmpty()) {
				result = temp;
			} 
		}
		return result;
	}

	public String getColumnPath() {
		return columnPath;
	}

}
