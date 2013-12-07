package gui;

/**
 * @author Andreas Johnstad
 *
 */
public class LoginData {
	private final static String USER_NAME = "root", PASSWORD = "root";  //correct username and password
	

	/**
	 * Method to compare user submittet login data
	 * With hardcoded data 
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean checkLoginInfo(String usrname, String pwd) {

		if (usrname.equals(USER_NAME) && pwd.equals(PASSWORD))
			return true;
		else
			return false;
	}
}
