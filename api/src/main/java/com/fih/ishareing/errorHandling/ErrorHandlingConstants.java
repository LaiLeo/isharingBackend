package com.fih.ishareing.errorHandling;

public class ErrorHandlingConstants {
	public enum ERROR_BUNDLE {
		invalidRequest("Parameter is invalid or cannot be recognized"),
		invalidPassword("Password is out-of-date or invalid"),
		invalidToken("Token was either missing or invalid."),
		invalidSignature("Signature is invalid"),
		internalError("internalError"),
		badFilename("File name format is invalid"),
		badDate("Date is invalid"),
		badEmail("Email is invalid"),
		badPassword("Password format is invalid"),
		userAccountExists("User account already exists"),
		userNotFound("User does not exist"),
		quotaLimitation("Exceed quota limitation"),
		userNotRegister("User account does not exist"),
		fubonAlreadyBindUser("Fubon account already bind by another user"),
		fubonAlreadyBind("Fubon account already bind by yourself"),
		twmAlreadyBindUser("Twm account already bind by another user"),
		twmAlreadyBind("Twm account already bind by yourself"),
		bannerNotFound("Banner does not exist"),
		npoNotFound("Npo does not exist"),
		npoAlreadyBind("Npo already bind"),
		npoBindNotFound("Npo binding does not exist"),
		eventNotFound("Event does not exist"),
		eventTypeConflict("Npo does not support donation events"),
		registeredNotFound("Event registered does not exist"),
		eventSkillGroupNotFound("Event skill group does not exist"),
		volunteerNumberError("Volunteer number must to be greater than current volunteer number"),
		fubonNotFound("Fubon binding does not exist"),
		twmNotFound("Twm binding does not exist"),
		accessTokenTimeout("Access token timeout"),
		userNpoExists("User npo already exists"),
		npoNameExists("Npo name already exists"),
		npoEnterpriseNameExists("Npo enterprise name already exists"),
		eventResultFileNotFound("Event result file does not exist"),
		eventAlreadyFocused("Event already focused"),
		eventAlreadyJoin("Event already join"),
		eventJoinNotFound("Event join does not exist"),
		eventAlreadyLeave("Event already leave"),
		eventExpired("Event register is expired"),
		eventRegisterNotFound("Event register does not exist"),
		eventRegisterExists("Event register already exists"),
		eventSkillGroupIdNotFound("Event skill group id does not exist"),
		registerNotFound("You didn’t register anything"),
		eventSkillGroupFulled("Event skill group fulled"),
		volunteerFulled("Event volunteer fulled"),
		userExists("User already exists"),
		birthdayNotFound("Birthday does not exist"),
		employeeSerialNumberNotFound("EmployeeSerialNumber does not exist"),
		enterpriseSerialNumberNotFound("EnterpriseSerialNumber does not exist"),
		eventSkillGroupListFormatError("Event skill group list format error"),
		securityIdNotFound("Security id does not exist"),
		appConfigsNotFound("App configs does not exist");

		private ERROR_BUNDLE(String desc) {
			this.desc = desc;
		}

		private String desc;

		public String getDesc() {
			return desc;
		}
	}
}
