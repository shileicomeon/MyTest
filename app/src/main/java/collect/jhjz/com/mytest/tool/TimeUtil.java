package collect.jhjz.com.mytest.tool;

import android.os.CountDownTimer;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

	public static class CountDownInfo {
		public CountDownTimer timer = null;
		public int remainSeconds = 0;
	}
	/**
	 * 对Button显示倒计时，多用于验证码
	 * @param button
	 * @param timeSeconds
	 * @param countTail		倒计时后缀，如“秒后再次发送”
	 * @return
	 */
	public static CountDownInfo startCountDown(final Button button,int timeSeconds,final String countTail){
		final String finishText = button.getText().toString();
		final CountDownInfo data = new CountDownInfo();
		CountDownTimer timer = new CountDownTimer(1000*timeSeconds, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				button.setEnabled(false);
				button.setText((millisUntilFinished / 1000) + countTail);
				data.remainSeconds = (int)(millisUntilFinished / 1000);
			}
			@Override
			public void onFinish() {
				button.setEnabled(true);
				button.setText(finishText);
			}
		};
		timer.start();
		return data;
	}

	public static String converTime(long timestamp) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > 24 * 60 * 60) {// 1天以上
			timeStr = timeGap / (24 * 60 * 60) + "天前";
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			timeStr = timeGap / (60 * 60) + "小时前";
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	public static String getStandardTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
		Date date = new Date(timestamp * 1000);
		sdf.format(date);
		return sdf.format(date);
	}

	/**
	 * 将字符串转化为日期
	 * 
	 * @param s
	 * @return
	 */
	public static Date convert2Date(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		// if (s.matches("\\d{4}-\\d{2}-\\d{2}")) {
		//
		// } else if (s.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
		// sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		// }else {
		// sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// }
		try {
			return sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param data
	 *            /Date(1400162583000)\
	 * @return
	 */
	public static String convertFromCSharp(String data) {
		if (data.trim().length()==0) {
			return "";
		}
		String l_str = data.replace("/Date(", "");
		l_str = l_str.replace(")/", "");

		Calendar calendar;
		long milliseconds;
		if (l_str.indexOf("+") != -1) {
			String str1 = l_str.substring(0, l_str.indexOf("+"));
			String str2 = l_str.substring(l_str.indexOf("+") + 1, l_str.length());
			milliseconds = Long.parseLong(str1);
			TimeZone zone = TimeZone.getTimeZone("GMT+" + str2);
			calendar = Calendar.getInstance(zone);
		} else {
			milliseconds = Long.parseLong(l_str);
			calendar = Calendar.getInstance(Locale.getDefault());
		}
		calendar.setTimeInMillis(milliseconds);
		Date d = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
		return sdf.format(d);
	}

}
