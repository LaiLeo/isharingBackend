package com.fih.ishareing.controller.base.vo.sync;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fih.ishareing.common.ApiConstants;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbsSync implements BaseSync {
	Map<ApiConstants.RESOURCE_TYPE, Set<String>> map;

	@Override
	@JsonIgnore
	public Map<ApiConstants.RESOURCE_TYPE, Set<String>> getAdditonalSyncResMap() {
		if (map == null) {
			map = new HashMap<ApiConstants.RESOURCE_TYPE, Set<String>>();
		}
		return map;
	}

	public void setAdditonalSyncResMap(Map<ApiConstants.RESOURCE_TYPE, Set<String>> map) {
		this.map = map;
	}

}
