package com.fih.ishareing.repository.pocservice.entity.interfaces;

import java.sql.Timestamp;

public interface TimestampLogEntity extends BaseEntity {
	void setCreatedTime(Timestamp createdTime);
	void setModifiedTime(Timestamp modifiedTime);

}
