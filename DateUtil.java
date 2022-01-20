package com.entropykorea.biztalkmng.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DateUtil {
	
	public static String getDate(String format, int addDays){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        cal.add(cal.DATE, addDays);

        return  dateFormat.format(cal.getTime());
    }
	
	public static String getMonth(String format, int addMonths){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        cal.add(cal.MONTH, addMonths);

        return  dateFormat.format(cal.getTime());
    }
	
	public static String getYear(String format, int addYears){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        cal.add(cal.YEAR, addYears);

        return  dateFormat.format(cal.getTime());
    }
	
	public static String getYesterday(String format){
        return getDate(format, -1);
    }
	
	public static String getToday(String format){
        return getDate(format, 0);
    }
	
	public static String getTomorrow(String format){
        return getDate(format, 1);
    }
	
	public static String getLastYear(String format){
        return getYear(format, -1);
    }
	
	public static String getYear(String format){
        return getYear(format, 0);
    }
	
	public static String getNextYear(String format){
        return getYear(format, 1);
    }
	
	public static String getLastDayMonthAlrimSummary(String date) {
		//2021-12
		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5));
		Calendar cal = new GregorianCalendar(year, month-1, 1);
		
		int lastDaysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		return String.valueOf(lastDaysOfMonth);
	}
	
	public static Boolean getBooleanAfterDayMonth(String format, String date) throws Exception {
		Boolean daysFlag = true;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date booleanDateFlag = dateFormat.parse(date);
		
		if(booleanDateFlag.after(dateFormat.parse(getDate("yyyy-MM", 0)))) { // 검색월이 금월보다 뒤라면
			daysFlag = true;
		} else {
			daysFlag = false;
		}
		
		return daysFlag;
	}
	
}
