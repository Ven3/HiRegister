package bai.utils;

import java.sql.DriverManager;
import com.mysql.jdbc.Connection;

public class DBManager{

	private static String dburl = "jdbc:mysql://119.29.38.36:3306/final?characterEncoding=utf8&useSSL=true";

	private static Connection connection = null;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(dburl, "root", "andy");
			System.out.println("Connected");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Connecting Error");
		}

	}

	public static Connection getConnection() {
		return connection;
	}

	public static void close() {
		try {

			if (!connection.isClosed()) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			System.out.println("Error Close");
		}

	}
	
	
	

}
