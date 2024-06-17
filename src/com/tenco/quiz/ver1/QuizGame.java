package com.tenco.quiz.ver1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.tenco.quiz.DBConnectionManager;

public class QuizGame {

	private static final String insertSql = "insert into quiz(question,answer) values(?,?) ";
	private static final String selectSql = "  select * from quiz  ";
	private static final String randomSql = "  select * from quiz order by rand() limit 1  ";

	public static void main(String[] args) {

		try (Connection conn = DBConnectionManager.getConnection(); Scanner sc = new Scanner(System.in)) {
			while (true) {
				printMenu();

				int choice = sc.nextInt(); // 블로킹

				if (choice == 1) {
					// 퀴즈 문제 추가 --> 너무 길어질 수 있으니 함수로 만들기
					addQuizQuestion(conn, sc);
				} else if (choice == 2) {
					// 퀴즈 문제 조회 --> 함수로 만들기
					viewQuizQuestion(conn);
				} else if (choice == 3) {
					// 퀴즈 게임 시작 --> 함수로 만들기
					playQuizGame(conn, sc);
				} else if (choice == 4) {
					System.out.println("프로그램을 종료합니다.");
					break;
				} else {
					System.out.println("잘못된 선택입니다. 다시 시도하세요.");
				}
			} // end of while

		} catch (Exception e) {
			e.printStackTrace();
		}

	} // end of main

	private static void printMenu() {
		System.out.println();
		System.out.println("-----------------------");
		System.out.println("1.퀴즈 문제 추가");
		System.out.println("2.퀴즈 문제 조회");
		System.out.println("3.퀴즈 게임 시작");
		System.out.println("4.종료");
		System.out.print("옵션을 선택하세요.");
	}

	private static void addQuizQuestion(Connection conn, Scanner sc) {
		System.out.println("퀴즈문제를 입력하세요.");
		sc.nextLine();
		String question = sc.nextLine();
		System.out.println("정답을 입력하세요.");
		String answer = sc.nextLine();

		// 자원을 나중에 일일히 닫기 보다는 try() <-- 안에 넣어주기
		try (PreparedStatement psmt = conn.prepareStatement(insertSql)) {

			psmt.setString(1, question);
			psmt.setString(2, answer);
			int rowsInsertedCount = psmt.executeUpdate(); // int로 응답이 리턴된다.
			System.out.println("추가된 행의 수 : " + rowsInsertedCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void viewQuizQuestion(Connection conn) {

		try (PreparedStatement psmt = conn.prepareStatement(selectSql)) {

			ResultSet resultSet = psmt.executeQuery();

			while (resultSet.next()) {
				System.out.println("문제 ID : " + resultSet.getInt("id"));
				System.out.println("문제 : " + resultSet.getString("question"));
				System.out.println("정답 : " + resultSet.getString("answer"));
				if (!resultSet.isLast()) {
					System.out.println("------------------------");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void playQuizGame(Connection conn, Scanner sc) {

		try (PreparedStatement psmt = conn.prepareStatement(randomSql)) {
			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {

				String question = rs.getString("question");
				String answer = rs.getString("answer");

				// 버그처리
				// 처음부터 int로 메뉴 선택이 아니라 String으로 받았다면 이런 버그는 생기지 않는다.
				// 숫자로 메뉴를 선택하고 \n이 자동입력되면서 스캐너가 넘어가게 된다.
				sc.nextLine();
				System.out.println("퀴즈 문제 : " + question);
				System.out.print("당신의 답 : ");
				String userAnswer = sc.nextLine();

				if (userAnswer.equalsIgnoreCase(answer)) {
					System.out.println("정답!");
				} else {
					System.out.println("오답");
					System.out.println("퀴즈 정답 : " + answer);
				}
			} else {
				System.out.println("죄송합니다. 아직 퀴즈 문제를 만들고 있습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

} // end of class
