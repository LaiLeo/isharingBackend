package com.fih.ishareing.controller.base.vo.sync;



import com.fih.ishareing.common.ApiConstants;

import java.util.Map;
import java.util.Set;

public interface BaseSync {
	public Map<ApiConstants.RESOURCE_TYPE, Set<String>> getAdditonalSyncResMap();
}
