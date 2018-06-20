package com.worksyun.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
    public static final String formatsss(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    	return dateFormat.format(date);
    }

    public static final String formatyyyyMMdd(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	return dateFormat.format(date);
    }
    
    public static final String format(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return dateFormat.format(date);
    }

    public static final String formatYYYYMMDDHHMM(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	return dateFormat.format(date);
    }
    public static final String formatYYYYMMDDHHMMSS_C(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    	return dateFormat.format(date);
    }

    public static final String formatYYYYMMDDHHMM_C(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    	return dateFormat.format(date);
    }
    public static final String formatYYYYMMDD(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	return dateFormat.format(date);
    }
    
    public static final Date formatToDate(String dateString) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return dateFormat.parse(dateString);
    }
    
    public static final Date dateyyyyMMdd(String dateString) throws ParseException{
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");  
    	return dateFormat.parse(dateString);
    };
    
    
    public static int comparedate(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
    
    
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
