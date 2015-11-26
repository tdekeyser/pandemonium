package framework;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	// simple string-based log class
	
	private StringBuilder logText;
	
	public Log() {
		this.logText = new StringBuilder();
	}
	
	public Log(String title) {
		 this.logText = new StringBuilder(title + System.lineSeparator() + System.lineSeparator());
	}
	
	public String fetchLog() {
		return logText.toString();
	}
	
	public void appendToLog(String info) {
		// append to the StringBuilder
		logText.append(getDate() + System.lineSeparator() + info + System.lineSeparator() + System.lineSeparator());
	}
	
	public void appendToLog(String info, String noDate) {
		logText.append(info + System.lineSeparator());
	}
	
	public void emptyLog() {
		logText.setLength(0);
	}
	
	private String getDate() {
		// gets date and time
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("E dd.MM.yyy 'at' hh:mm:ss a");
		return df.format(date);
	}
	
}
