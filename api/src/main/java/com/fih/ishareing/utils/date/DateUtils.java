package com.fih.ishareing.utils.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		try {
			String dateString = dateformat.format(date);

			return dateString;
		} catch (Exception e) {
			return null;
		}
	}

	public static Date addMilliseconds(Date date, long milliseconds) {
		return new Date(date.getTime() + milliseconds);
	}

	public static long getUnixTimeUTC() {
		return Instant.now().toEpochMilli();
	}

	public static long getUnixTimeUTC(long add) {
		return Instant.now().toEpochMilli() + add;
	}

	public static boolean UTCtimeIn(long time, long limitsMs) {
		long now = getUnixTimeUTC();
		long upperTime = now + limitsMs;
		long lowerTime = now - limitsMs;

		if (time > upperTime)
			return false;
		else if (time < lowerTime)
			return false;

		return true;
	}

	public static boolean UTCtimeNotIn(long time, long limitsMs) {
		return !UTCtimeIn(time, limitsMs);
	}

	public static Date toDate(String string) {
		DateFormat formatter = null;
		try {
			formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			return formatter.parse(string);
		} catch (ParseException e3) {
			try {
				formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				return formatter.parse(string);
			} catch (ParseException e4) {
				try {
					formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
					return formatter.parse(string);
				} catch (ParseException e5) {
					try {
						formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
						return formatter.parse(string);
					} catch (ParseException e6) {
						try {
							formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
							return formatter.parse(string);
						} catch (ParseException e) {
							try {
								formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								return formatter.parse(string);
							} catch (ParseException e1) {
								try {
									formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
									return formatter.parse(string);
								} catch (ParseException e7) {
									try {
										formatter = new SimpleDateFormat("yyyy-MM-dd");
										return formatter.parse(string);
									} catch (ParseException e2) {
										return null;
									}
								}


							}
						}
					}
				}
			}
		}

	}

	public static Date toDate(String dateString, String format) {
		DateFormat formatter = null;

		try {
			formatter = new SimpleDateFormat(format);
			return formatter.parse(dateString);
		} catch (ParseException e) {
			throw new IllegalArgumentException(String.format("DateTime format must match '%s'", format));
		}
	}

	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_DATE_REGISTER_FORMAT = "yyyy-MM-dd HH:mm";

	public static boolean isVaidDate(String value) {
		return isValidFormat(DEFAULT_DATE_TIME_FORMAT, value) || isValidFormat(DEFAULT_DATE_FORMAT, value);
	}

	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			// do nothing...
		}
		return date != null;
	}

	public static Timestamp GetNowTimestamp() {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public static String getRegisterDateString(Long now) {
		Date date = new Date();
		date.setTime(now);
		String registerDate = new SimpleDateFormat(DEFAULT_DATE_REGISTER_FORMAT).format(date);

		return registerDate;

	}
}
