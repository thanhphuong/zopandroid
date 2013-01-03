package vn.fiosoft.log;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import android.util.Log;

public class Log4J {
	
	public static Boolean mWriteToFile = true;
	public static int mLevel = Log.ERROR;	
	public static final String mFileName = "/sdcard/log.in";
	
	//log v
	public static void v(String tag, String msg) {
		
		if (Log.VERBOSE < mLevel)
			return;
		
		if (mWriteToFile == false){
			Log.v(tag, msg);
			return;
		}
		writeToFile(tag, msg);
	}
	
	//log d
	public static void d(String tag, String msg) {
			
			if (Log.DEBUG < mLevel)
				return;
			
			if (mWriteToFile == false){
				Log.v(tag, msg);
				return;
			}
			writeToFile(tag, msg);
		}
	
	//log i
	public static void i(String tag, String msg) {
		
		if (Log.INFO < mLevel)
			return;
		
		if (mWriteToFile == false){
			Log.v(tag, msg);
			return;
		}
		writeToFile(tag, msg);
	}
	
	//log w
	public static void w(String tag, String msg) {
		
		if (Log.WARN < mLevel)
			return;
		
		if (mWriteToFile == false){
			Log.v(tag, msg);
			return;
		}
		writeToFile(tag, msg);
	}
	
	//log e
	public static void e(String tag, String msg) {
		
		if (Log.ERROR < mLevel)
			return;
		
		if (mWriteToFile == false){
			Log.v(tag, msg);
			return;
		}
		writeToFile(tag, msg);
	}
	
	private static void writeToFile(String tag, String msg){
		try {
			FileOutputStream fos = new FileOutputStream(mFileName, true);
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
			Calendar c = Calendar.getInstance();
			
			int h = c.get(Calendar.HOUR_OF_DAY);
			int m = c.get(Calendar.MINUTE);
			int s = c.get(Calendar.SECOND);
			
			out.write(h + ":" + m + ":" + s + " | " + tag + " | " + msg + "\n");
			out.close();
			fos.close();
		} catch (Exception ignore) {
			// Just do not crash because of log problem.	
		}
	}
	
	
}
