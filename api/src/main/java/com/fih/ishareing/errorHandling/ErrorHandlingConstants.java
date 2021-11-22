package com.fih.ishareing.errorHandling;

public class ErrorHandlingConstants {
	public enum ERROR_BUNDLE {
		invalidRequest("Parameter is invalid or cannot be recognized"),
		invalidPassword("Password is out-of-date or invalid"),
		invalidToken("Token was either missing or invalid."),
		invalidSignature("Signature is invalid"),
		internalError("internalError"),
		accountNotFound("Account does not exist"),
		anchorConflicts("Anchor conflicts was found"),
		anchorNotFound("Anchor does not exist"),
		anchorPidExists("Anchor MAC address already exists"),
		badCycle("Bad cycle format"),
		badFilename("File name format is invalid"),
		badDate("Date is invalid"),
		badEmail("Email is invalid"),
		userAccountExists("User account already exists"),
		userConflicts("User conflicts was found"),
		userEmailExists("User email already exists"),
		userNotFound("User does not exist"),
		userRoleCodeExists("User role code already exists"),
		userAlreadyBindRole("User already bind one role"),
		userRoleNotFound("User role does not exist"),
		quotaLimitation("Exceed quota limitation");

		private ERROR_BUNDLE(String desc) {
			this.desc = desc;
		}

		private String desc;

		public String getDesc() {
			return desc;
		}
	}
}
