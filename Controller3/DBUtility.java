package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {
	public static Connection getConnection() {
		Connection connection = null;
		// 1. MySql database class를 로드한다.
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 2. 주소, 아이디, 비밀번호를 통해서 접속요청한다.
			connection = DriverManager.getConnection("jdbc:mysql://localhost/balletdb", "root", "123456");
			//StudentController.callAlert("연결성공:데이터베이스 성공");
		} catch (Exception e) {
			StudentController.callAlert("연결실패:데이터베이스 실패");
			return null;
		}
		return connection;
	}
}
