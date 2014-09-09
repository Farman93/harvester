package stringProcedures;
//Procedure for checking login string that it consists of English letters and numbers   
public class LoginString {
	public static boolean checkChar(char c) {
		if (c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
			return true;
		else
			return false;
	}
	public static boolean check(String arg) {
		for (int i = 0; i < arg.length(); ++i) {
			if (checkChar(arg.charAt(i)))
				continue;
			else
				return false;
		}
		return true;
	}
}
