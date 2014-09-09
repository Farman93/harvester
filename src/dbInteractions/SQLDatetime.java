package dbInteractions;

import java.text.*;

public class SQLDatetime {
	public int d;
	public int mo;
	public int y;
	public int h;
	public int mi;
	public int s;
	public SQLDatetime() {
		d = -1;
		mo = -1;
		y = -1;
		h = -1;
		mi = -1;
		s = -1;
	}
	static public boolean isValidDatetime(String input) {
		String form = "dd.MM.yyyy HH:mm";
		try{
			SimpleDateFormat format = new SimpleDateFormat(form);
			format.setLenient(false);
			format.parse(input);
		} catch (ParseException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}
