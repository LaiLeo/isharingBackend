package com.fih.ishareing.common;

public class ApiConstants {

	public static final String ACTIVE_FILETER = "active = true";
	public static final String DEFAULT_EXPORT_FILE_NAME = "export.xls";
	public static final String MIME_TYPE_EXCEL = "application/vnd.ms-excel";

	public static final String ENV_FILE_DIR_LOCATION = "file.dir.location";

	public static final Short Untreated = 10;
	public static final Short Treating = 20;
	public static final Short Treated = 30;
	public static final Short[] TASK_ACTIONS = new Short[] { Untreated, Treating, Treated };

	public enum FILE_DIR {
		Map, PocGroup
	}

	public enum PAGE_SIZE_LIMIT {
		JSON(50), EXCEL(5000);

		private int size;

		private PAGE_SIZE_LIMIT(int size) {
			this.size = size;
		}

		public int getSize() {
			return size;
		}
	}

	public enum RESOURCE_TYPE {
		object
	}

}
