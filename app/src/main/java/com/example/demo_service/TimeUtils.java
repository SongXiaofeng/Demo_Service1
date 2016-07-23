package com.example.demo_service;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;



/**
 * TimeUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE    = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final long DAY_MILLISECONDS = (1000L * 60 * 60 * 24);
    public static final String BEGIN = "begin";
	public static final String END = "end";

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to string
     * 
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
    
    /**  
     * ������������֮����������  
     * @param smdate ��С��ʱ�� 
     * @param bdate  �ϴ��ʱ�� 
     * @return ������� 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/DAY_MILLISECONDS;  
        return Integer.parseInt(String.valueOf(between_days));           
    } 
    
    /**
     *  ��ȡ��ǰdata�����ܵĵ�һ������һ��
     * @param date
     * @return
     */
    public static Map<String, Date> getWeekStartEnd(Date date) {
		Map<String, Date> map = new HashMap<String, Date>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.getDefault());
			String str = sdf.format(date);
			Date date2 = simpleDateFormat.parse(str);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date2);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			date2 = cal.getTime();
			map.put(BEGIN, date2);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			Date date3 = new Date(cal.getTimeInMillis() + DAY_MILLISECONDS - 1000);
			map.put(END, date3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
	}
    
    /**
     * ��ȡ��ǰdata�����µĵ�һ������һ��
     * @param date
     * @return
     */
	public static Map<String, Date> getMonthStartEnd(Date date) {
		Map<String, Date> map = new HashMap<String, Date>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01 00:00:00", Locale.getDefault());
			String str = sdf.format(date);
			Date date2 = simpleDateFormat.parse(str);
			map.put(BEGIN, date2);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date2);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			Date date3 = new Date(cal.getTimeInMillis() +DAY_MILLISECONDS - 1000);
			map.put(END, date3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * ��ȡ�����������·�֮��
	 * 
	 * @param calendarBirth
	 * @param calendarNow
	 * @return
	 */
	public static int getMonthsOfAge(Calendar calendarBirth, Calendar calendarNow) {
		return (calendarNow.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR)) * 12
				+ calendarNow.get(Calendar.MONTH) - calendarBirth.get(Calendar.MONTH);
	}

	/**
	 * �ж���һ���Ƿ����µ�
	 * 
	 * @param calendar
	 * @return
	 */
	public static boolean isEndOfMonth(Calendar calendar) {
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
			return true;
		return false;
	}
	
	/**
	 * ��ȡ��Ӧ��������ʱ�����������
	 * @param calendarBirth
	 * @param calendarNow
	 * @return
	 */
	public static int[] getNeturalAge(Calendar calendarBirth, Calendar calendarNow) {
		int diffYears = 0, diffMonths, diffDays;
		int dayOfBirth = calendarBirth.get(Calendar.DAY_OF_MONTH);
		int dayOfNow = calendarNow.get(Calendar.DAY_OF_MONTH);
		if (dayOfBirth <= dayOfNow) {
			diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
			diffDays = dayOfNow - dayOfBirth;
		} else {
			if (isEndOfMonth(calendarBirth)) {
				if (isEndOfMonth(calendarNow)) {
					diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
					diffDays = 0;
				} else {
					calendarNow.add(Calendar.MONTH, -1);
					diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
					diffDays = dayOfNow + 1;
				}
			} else {
				if (isEndOfMonth(calendarNow)) {
					diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
					diffDays = 0;
				} else {
					calendarNow.add(Calendar.MONTH, -1);// �ϸ���
					diffMonths = getMonthsOfAge(calendarBirth, calendarNow);
					// ��ȡ�ϸ�������һ��
					int maxDayOfLastMonth = calendarNow.getActualMaximum(Calendar.DAY_OF_MONTH);
					if (maxDayOfLastMonth > dayOfBirth) {
						diffDays = maxDayOfLastMonth - dayOfBirth + dayOfNow;
					} else {
						diffDays = dayOfNow;
					}
				}
			}
		}
		// �����·�ʱ��û�п�����
		diffYears = diffMonths / 12;
		diffMonths = diffMonths % 12;
		return new int[] { diffYears, diffMonths, diffDays };
	}
    
    
}