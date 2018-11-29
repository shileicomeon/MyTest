package collect.jhjz.com.mytest.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 类DateUtil.java的实现描述：时间工具类
 * 
 * <pre>
 * 时间工具类中包含的方法：
 * 1）format：将传入的java.util.Date类型的时间转换为字符串数据，支持pattern格式定制
 * 2）parse：将出入的字符串解析成java.util.Date类型的时间数据，支持pattern格式定制
 * 3）addNumWithType：
 * 4）getFirstDaily：
 * 5）getLastDaily：
 * 6）getFirstMonthly：
 * 7）getLastMonthly：
 * 8）getFirstQuarter：
 * 9）getLastQuarter：
 * </pre>
 * 
 * @author <a href="mailto:wywuzh@163.com">伍章红</a>, 2014-4-24 下午1:57:39
 * @version 4.0.0
 * @since JDK 1.6.0_20
 */
public class DateUtil {

	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PIN_PATTERN_DATE_TIME = "yyyyMMddHHmmss";
	public static final String PIN_PATTERN_DATE_MINUTE = "yyyyMMddHHmm";
	public static final String PIN_PATTERN_DATE_HOUR = "yyyyMMddHH";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_TIME = "HH:mm:ss";
	public static final String PATTERN_YM = "yyyy-MM";
	public static final int FIELD_YEAR = Calendar.YEAR;
	public static final int FIELD_MONTH = Calendar.MONTH;
	public static final int FIELD_DATE = Calendar.DATE;
	public static final int FIELD_DAY_OF_MONTH = Calendar.DAY_OF_MONTH;
	public static final int FIELD_HOUR = Calendar.HOUR;
	public static final int FIELD_HOUR_OF_DAY = Calendar.HOUR_OF_DAY;
	public static final int FIELD_MINUTE = Calendar.MINUTE;
	public static final int FIELD_SECOND = Calendar.SECOND;
	public static final int FIELD_MILLISECOND = Calendar.MILLISECOND;
    /**
     * 变量：日期格式化类型 - 格式:yyyyMMddHHmmss
     * 
     */
    public static final int DATE_TIME_NO_SLASH = 5;
	/**
	 * 时间格式化工具
	 */
	private static SimpleDateFormat dateFormat;

	/**
	 * 获取SimpleDateFormat实例
	 * 
	 * <pre>
	 *  创建SimpleDateFormat实例，pattern默认采用“yyyy-MM-dd HH:mm:ss”格式
	 *  
	 *  DateUtil工具在创建SimpleDateFormat实例时，采用single单例模式，确保DateUtil类对象中永远只有一个SimpleDateFormat实例
	 * </pre>
	 * 
	 * @return SimpleDateFormat实例
	 */
	public static SimpleDateFormat getInstance() {
		if (null == dateFormat) {
			dateFormat = new SimpleDateFormat(PATTERN_DATE_TIME);
		}
		return dateFormat;
	}

	/**
	 * 获取pattern格式的SimpleDateFormat实例
	 * 根据传入的pattern格式创建SimpleDateFormat实例，如果传入pattern参数为空，则pattern默认采用“yyyy-MM-dd HH:mm:ss”格式
	 * DateUtil工具在创建SimpleDateFormat实例时，采用single单例模式，确保DateUtil类对象中永远只有一个SimpleDateFormat实例
	 * 
	 * @param pattern
	 * @return
	 */
	public static SimpleDateFormat getInstance(String pattern) {
		if (null == dateFormat) {
			if (!StringUtils.isEmpty(pattern)) {
				dateFormat = new SimpleDateFormat(pattern);
			} else {
				dateFormat = getInstance();
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}

	/**
	 * 将传入时间格式化成一个字符串，默认采用“yyyy-MM-dd HH:mm:ss”格式
	 * 
	 * @param date
	 * @return 返回“yyyy-MM-dd HH:mm:ss”格式字符串，如果传入时间为空，返回null
	 */
	public static String format(Date date) {
		if (null == date) {
			return null;
		}

		return getInstance().format(date);
	}
	
	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getNowDate(){
		return DateUtil.format(new Date(), DateUtil.PATTERN_DATE);
	}

	/**
	 * 将传入时间格式化成一个字符串
	 * 
	 * <pre>
	 *  返回pattern格式字符串，如果pattern格式传入为空，默认采用“yyyy-MM-dd HH:mm:ss”格式；
	 *  如果传入时间为空，返回null
	 * </pre>
	 * 
	 * @param date
	 * @param pattern
	 * @return 返回“yyyy-MM-dd HH:mm:ss”格式字符串，如果传入时间为空，返回null
	 */
	public static String format(Date date, String pattern) {
		if (null == date) {
			return null;
		}

		return getInstance(pattern).format(date);
	}

	/**
	 * 将传入的时间字符串解析成java.util.Date时间格式
	 * 
	 * <pre>
	 * 如果传入一个空时间字符串，则返回空对象
	 * </pre>
	 * 
	 * @param parseDate
	 * @return
	 */
	public static Date parse(String parseDate) {
		Date date = null;
		if (!StringUtils.isEmpty(parseDate)) {
			try {
				date = getInstance().parse(parseDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	}

	/**
	 * 将传入的时间字符串解析成java.util.Date时间格式
	 * 
	 * <pre>
	 * 如果传入一个空时间字符串，则返回空对象
	 * </pre>
	 * 
	 * @param parseDate
	 * @param pattern
	 * @return
	 */
	public static Date parse(String parseDate, String pattern) {
		Date date = null;
		if (!StringUtils.isEmpty(parseDate)) {
			try {
				date = getInstance(pattern).parse(parseDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 根据时间类型添加num值
	 * 
	 * <pre>
	 *  如：addNumWithType方法中传入的type类型为Calendar.YEAR，num为2，则表示在date时间上加两年得到的时间；
	 *      num为-2，则表示在date时间上减去两年得到的时间。
	 *  type类型为Calendar.YEAR、Calendar.MONTH、Calendar.DATE等
	 * </pre>
	 * 
	 * @param date
	 *            时间
	 * @param fieldType
	 *            时间类型
	 * @param num
	 *            添加值
	 * @return
	 */
	public static Date addNumWithType(Date date, int fieldType, int num) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(fieldType, num);
		return calendar.getTime();
	}

	/**
	 * 获取传入日期当天的开始时间
	 * 
	 * @param daily
	 * @return
	 */
	public static Date getFirstDaily(Date daily) {
		if (null == daily) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(daily);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取传入日期当天的开始时间
	 * 
	 * @param daily
	 *            yyyy-MM-dd格式
	 * @return 如果传入参数值为空，返回0
	 */
	public static long getFirstDailyTime(String daily) {
		if (StringUtils.isEmpty(daily)) {
			return 0;
		}
		Date firstDaily = getFirstDaily(parse(daily, PATTERN_DATE));
		return (null == firstDaily) ? 0 : firstDaily.getTime();
	}

	/**
	 * 获取传入日期当天的开始时间
	 * 
	 * @param daily
	 * @return 如果传入参数值为空，返回0
	 */
	public static long getFirstDailyTime(Date daily) {
		if (null == daily) {
			return 0;
		}
		Date firstDaily = getFirstDaily(daily);
		return (null == firstDaily) ? 0 : firstDaily.getTime();
	}

	/**
	 * 获取传入日期当天的结束时间
	 * 
	 * @param daily
	 * @return
	 */
	public static Date getLastDaily(Date daily) {
		if (null == daily) {
			return null;
		}
		Date firstDaily = getFirstDaily(daily);
		if (null == firstDaily) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDaily);
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.MILLISECOND, -1);

		return calendar.getTime();
	}

	/**
	 * 获取传入日期当天的结束时间
	 * 
	 * @param daily
	 *            yyyy-MM-dd格式
	 * @return 如果传入参数值为空，返回0
	 */
	public static long getLastDailyTime(String daily) {
		if (StringUtils.isEmpty(daily)) {
			return 0;
		}

		Date lastDaily = getLastDaily(parse(daily, PATTERN_DATE));
		return lastDaily == null ? 0 : lastDaily.getTime();
	}

	/**
	 * 获取传入日期当天的结束时间
	 * 
	 * @param daily
	 * @return
	 */
	public static long getLastDailyTime(Date daily) {
		Date lastDaily = getLastDaily(daily);
		return lastDaily == null ? 0 : lastDaily.getTime();
	}

	/**
	 * 获取传入月当月的开始时间
	 * 
	 * @param monthly
	 * @return 如果传入参数值为空，返回空
	 */
	public static Date getFirstMonthly(Date monthly) {
		if (null == monthly) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(monthly);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取传入月当月的开始时间
	 * 
	 * @param monthly
	 *            yyyy-MM格式
	 * @return 如果传入参数值为空，返回空
	 */
	public static Date getFirstMonthly(String monthly) {
		if (StringUtils.isEmpty(monthly)) {
			return null;
		}
		return getFirstMonthly(parse(monthly, PATTERN_YM));
	}

	/**
	 * 获取传入月当月的开始时间
	 * 
	 * @param monthly
	 *            yyyy-MM格式
	 * @return 如果传入参数值为空，返回0
	 */
	public static long getFirstMonthlyTime(String monthly) {
		if (StringUtils.isEmpty(monthly)) {
			return 0;
		}

		Date firstMonthly = getFirstMonthly(parse(monthly, PATTERN_YM));
		return firstMonthly == null ? 0 : firstMonthly.getTime();
	}

	/**
	 * 获取传入月当月的开始时间
	 * 
	 * @param monthly
	 * @return 如果传入参数值为空，返回0
	 */
	public static long getFirstMonthlyTime(Date monthly) {
		Date firstMonthly = getFirstMonthly(monthly);
		return firstMonthly == null ? 0 : firstMonthly.getTime();
	}

	/**
	 * 获取传入月当月的结束时间
	 * 
	 * @param monthly
	 * @return 如果传入参数值为空，返回null
	 */
	public static Date getLastMonthly(Date monthly) {
		if (null == monthly) {
			return null;
		}

		Date firstMonthly = getFirstMonthly(monthly);
		if (null == firstMonthly) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstMonthly);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

	/**
	 * 获取传入月当月的结束时间
	 * 
	 * @param monthly
	 * yyyy-MM格式
	 * @return 如果传入参数值为空，返回null
	 */
	public static Date getLastMonthly(String monthly) {
		if (StringUtils.isEmpty(monthly)) {
			return null;
		}

		return getLastMonthly(parse(monthly, PATTERN_YM));
	}

	/**
	 * 获取传入月当月的结束时间
	 * 
	 * @param monthly
	 *            yyyy-MM格式
	 * @return 如果传入参数值为空，返回0
	 */
	public static long getLastMonthlyTime(String monthly) {
		if (StringUtils.isEmpty(monthly)) {
			return 0;
		}

		Date lastMonthly = getLastMonthly(parse(monthly, PATTERN_YM));
		return lastMonthly == null ? 0 : lastMonthly.getTime();
	}

	/**
	 * 获取传入月当月的结束时间
	 * 
	 * @param monthly
	 *            yyyy-MM格式
	 * @return 如果传入参数值为空，返回0
	 */
	public static long getLastMonthlyTime(Date monthly) {
		Date lastMonthly = getLastMonthly(monthly);
		return lastMonthly == null ? 0 : lastMonthly.getTime();
	}

	/**
	 * 获取传入时间所在季度的第一天
	 * 
	 * @param date
	 * @return 传入时间所在季度的第一天
	 */
	public static Date getFirstQuarter(Date date) {
		if (null == date) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(FIELD_DAY_OF_MONTH, 1);
		calendar.set(FIELD_HOUR_OF_DAY, 0);
		calendar.set(FIELD_MINUTE, 0);
		calendar.set(FIELD_SECOND, 0);
		calendar.set(FIELD_MILLISECOND, 0);
		int month = calendar.get(FIELD_MONTH);

		if (month < 3) {
			month = 0;
		} else if (month >= 3 && month < 6) {
			month = 3;
		} else if (month >= 6 && month < 9) {
			month = 6;
		} else {
			month = 9;
		}
		calendar.set(FIELD_MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 获取传入时间所在季度的最后一天
	 * 
	 * @param date
	 * @return 传入时间所在季度的最后一天
	 */
	public static Date getLastQuarter(Date date) {
		if (null == date) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(FIELD_DAY_OF_MONTH, 1);
		calendar.set(FIELD_HOUR_OF_DAY, 0);
		calendar.set(FIELD_MINUTE, 0);
		calendar.set(FIELD_SECOND, 0);
		calendar.set(FIELD_MILLISECOND, 0);

		int month = calendar.get(FIELD_MONTH);
		if (month < 3) {
			month = 2;
		} else if (month >= 3 && month < 6) {
			month = 5;
		} else if (month >= 6 && month < 9) {
			month = 8;
		} else {
			month = 11;
		}
		calendar.set(FIELD_MONTH, month);
		calendar.add(FIELD_MONTH, 1);
		calendar.add(FIELD_MILLISECOND, -1);
		return calendar.getTime();
	}
	
	/**
	 * 计算出离beginDate日期len天的日期,若len小于0表示当前日期之前len天，若datas大于0表当前日期之后len天
	 * @param date 传进来的时间
	 * @param len 需要改变的天数，正负均可
	 * @return
	 */
	public static String getDate(String date, int len) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(date));
			cal.add(Calendar.DATE, len);
			return sdf.format(cal.getTime());
		} catch (Exception e) {
			return date;
		}
	}

	/**
	 * 计算出离beginDate日期len天的日期,若len小于0表示当前日期之前len天，若datas大于0表当前日期之后len天
	 * @param beginDate 传进来的时间
	 * @param len 需要改变的天数，正负均可
	 * @return
	 */
	public static Date getDate(Date beginDate, int len) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			cal.add(Calendar.DATE, len);
			return cal.getTime();
		} catch (Exception e) {
			return beginDate;
		}
	}

	/**
	 * 计算出离beginDate日期datas天的日期,若datas小于0表示当前日期之前datas天，若datas大于0表当前日期之后datas天
	 * @return 得到日期
	 */
	public static Date getDay(Date beginDate, int datas) {
		Calendar beginCal = Calendar.getInstance();
		beginCal.setTime(beginDate);
		GregorianCalendar calendar = new GregorianCalendar(
				beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),
				beginCal.get(Calendar.DATE));
		calendar.add(GregorianCalendar.DATE, datas);
		String begin = new java.sql.Date(calendar.getTime().getTime())
				.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = null;
		try {
			endDate = sdf.parse(begin);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return endDate;
	}

	public static void main(String[] args) {
		Date cuDate = null;
		Date cuDate2 = null;
		
		try {
			Date paraDate = new SimpleDateFormat("yyyy-MM-dd")
					.parse("2014-10-09");
			System.out.println("paraDate:" + paraDate);
			cuDate = getDate(paraDate, 31);
			cuDate2 = getDay(paraDate, 31);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		System.out.println(DateUtil.format(cuDate, PATTERN_DATE));
		System.out.println(DateUtil.format(cuDate2, PATTERN_DATE));

		// SimpleDateFormat instance = getInstance();
		// System.out.println(instance.format(new Date()));
		// System.out.println(format(new Date()));
		//
		// String date = "2014-10";
		// Date parseDate = parse(date, PATTERN_YM);
		// System.out.println("parseDate:" + format(parseDate,
		// PATTERN_DATE_TIME));
		//
		// System.out.println("FirstQuarter:"
		// + format(getFirstQuarter(parseDate), PATTERN_DATE_TIME));
		// System.out.println("LastQuarter:"
		// + format(getLastQuarter(parseDate), PATTERN_DATE_TIME));
	}
	
	
	
	/** 计算年龄 */
	public static String getAge(String birthDay) throws Exception {
	        Calendar cal = Calendar.getInstance();

//	        if (cal.before(birthDay)) {
//	            throw new IllegalArgumentException(
//	                "The birthDay is before Now.It's unbelievable!");
//	        }
	        
	        int yearNow = cal.get(Calendar.YEAR);
	        int monthNow = cal.get(Calendar.MONTH)+1;
	        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
	        
	        cal.setTime(parse(birthDay));
	        int yearBirth = cal.get(Calendar.YEAR);
	        int monthBirth = cal.get(Calendar.MONTH);
	        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
	        int age = yearNow - yearBirth;
	        if (monthNow <= monthBirth) {
	            if (monthNow == monthBirth) {
	                //monthNow==monthBirth
	                if (dayOfMonthNow < dayOfMonthBirth) {
	                    age--;
	                } 
	            } else {
	                //monthNow>monthBirth
	                age--;
	            }
	        } 

	        return age + "";
	    }
	/**
     * 变量：日期格式化类型 - 格式:yyyy/MM/dd
     */
	public static final int DEFAULT = 0;
	public static final int YM = 1;

    /**
     * 变量：日期格式化类型 - 格式:yyyy-MM-dd
     * 
     */
    public static final int YMR_SLASH = 11;

    /**
     * 变量：日期格式化类型 - 格式:yyyyMMdd
     * 
     */
    public static final int NO_SLASH = 2;

    /**
     * 变量：日期格式化类型 - 格式:yyyyMM
     * 
     */
    public static final int YM_NO_SLASH = 3;

    /**
     * 变量：日期格式化类型 - 格式:yyyy/MM/dd HH:mm:ss
     * 
     */
    public static final int DATE_TIME = 4;

    /**
     * 变量：日期格式化类型 - 格式:yyyyMMddHHmmss
     * 
     */

    /**
     * 变量：日期格式化类型 - 格式:yyyy/MM/dd HH:mm
     * 
     */
    public static final int DATE_HM = 6;

    /**
     * 变量：日期格式化类型 - 格式:HH:mm:ss
     * 
     */
    public static final int TIME = 7;

    /**
     * 变量：日期格式化类型 - 格式:HH:mm
     * 
     */
    public static final int HM = 8;
    
    /**
     * 变量：日期格式化类型 - 格式:HHmmss
     * 
     */
    public static final int LONG_TIME = 9;
    /**
     * 变量：日期格式化类型 - 格式:HHmm
     * 
     */
    
    public static final int SHORT_TIME = 10;

    /**
     * 变量：日期格式化类型 - 格式:yyyy-MM-dd HH:mm:ss
     */
    public static final int DATE_TIME_LINE = 12;
	public static String dateToStr(Date date, int type) {
        switch (type) {
        case DEFAULT:
            return dateToStr(date);
        case YM:
            return dateToStr(date, "yyyy/MM");
        case NO_SLASH:
            return dateToStr(date, "yyyyMMdd");
        case YMR_SLASH:
        	return dateToStr(date, "yyyy-MM-dd");
        case YM_NO_SLASH:
            return dateToStr(date, "yyyyMM");
        case DATE_TIME:
            return dateToStr(date, "yyyy/MM/dd HH:mm:ss");
        case DATE_TIME_NO_SLASH:
            return dateToStr(date, "yyyyMMddHHmmss");
        case DATE_HM:
            return dateToStr(date, "yyyy/MM/dd HH:mm");
        case TIME:
            return dateToStr(date, "HH:mm:ss");
        case HM:
            return dateToStr(date, "HH:mm");
        case LONG_TIME:
            return dateToStr(date, "HHmmss");
        case SHORT_TIME:
            return dateToStr(date, "HHmm");
        case DATE_TIME_LINE:
            return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
        default:
            throw new IllegalArgumentException("Type undefined : " + type);
        }
    }
	public static String dateToStr(Date date, String pattern) {
	       if (date == null || date.equals(""))
	    	 return null;
	       SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	       return formatter.format(date);
 } 

	public static String dateToStr(Date date) {
     return dateToStr(date, "yyyy/MM/dd");
	}
}
