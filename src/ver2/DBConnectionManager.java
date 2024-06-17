package ver2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 커넥션 풀을 활용하는 예제로 수정해보자. HikariCP-5.1.0 jar
 */

public class DBConnectionManager {

	private static HikariDataSource dataSource;

	private static final String URL = "jdbc:mysql://localhost:3306/studentdb?serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASSWORD = "asd123";

	static {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(URL);
		config.setUsername(USER);
		config.setPassword(PASSWORD);
		config.setMaximumPoolSize(10);
		dataSource = new HikariDataSource(config);
	}

	public static Connection getConnection() throws SQLException {
		System.out.println("HikariCP 를 사용한 Data Source 활용");
		return dataSource.getConnection();
	}

	public static void main(String[] args) {
		try {
			Connection conn = DBConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

} // end of class
