package com.tenco.quiz.ver4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginExample {

	// 개발 테스트를 위한 로깅 처리 및 로그
	private static final Logger LOGGER = Logger.getLogger(LoginExample.class.getName());

	public static void main(String[] args) {

		// DataSource 를 활용한 Connection 객체를 사용하자.

		try {
			// HikariCP가 담김
			Connection conn = DBConnectionManager.getConnection();
			// username, password를 받아서 확인해야 한다.

			Scanner sc = new Scanner(System.in);
			System.out.println("username를 입력하세요.");
			String username = sc.nextLine();

			System.out.println("password를 입력하세요.");
			String pwd = sc.nextLine();

			if (authenticateUser(conn, username, pwd)) {
				System.out.println("로그인 성공");
			} else {
				System.out.println("로그인 실패");
			}

		} catch (SQLException e) {
			LOGGER.log(Level.INFO, "MySQL 연결 오류");
			e.printStackTrace();
		}

	} // end of main

	private static boolean authenticateUser(Connection conn, String username, String password) {
		String query1 = "  select * from users where username = ?  ";
		String query2 = "  select * from users where username = ? and password = ?  ";

		boolean result = false;

		try {
			PreparedStatement pstmt1 = conn.prepareStatement(query1);
			pstmt1.setString(1, username);
			ResultSet rs1 = pstmt1.executeQuery();

			PreparedStatement pstmt2 = conn.prepareStatement(query2);
			pstmt2.setString(1, username);
			pstmt2.setString(2, password);
			ResultSet rs = pstmt1.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

} // end of class
