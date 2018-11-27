package cn.lsd.app.IM.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {
	static public String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}
}
