package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {

	public static Connection getConnection( ) {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/tomin?serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "zxc039800";
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(dbURL, dbID, dbPassword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
