package ver3;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class QuizRepositoryTest1 {

	public static void main(String[] args) {

		// 메서드 호출해서 실행 확인 디버깅 확인 테스트 반드시 한다!!!

		// QuizRepository 구현 클래스 테스트

		// 실행에 흐름을 여러분이 직접 만들어 보기

		QuizRepositoryImpl quiz = new QuizRepositoryImpl();
		Scanner sc = new Scanner(System.in);
		boolean flag = true;

		final String ADD = "1";
		final String SHOW = "2";
		final String START = "3";
		final String END = "4";

		while (flag) {

			System.out.println();
			System.out.println("-----------------------");
			System.out.println("1.퀴즈 문제 추가");
			System.out.println("2.퀴즈 문제 조회");
			System.out.println("3.퀴즈 게임 시작");
			System.out.println("4.종료");
			System.out.print("옵션을 선택하세요.");

			String choice = sc.nextLine();
			if (choice.equals(ADD)) {
				System.out.println("질문 입력");
				String question = sc.nextLine();
				System.out.println("답변 입력");
				String answer = sc.nextLine();
				try {
					quiz.addQuizQuestion(question, answer);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (choice.equals(SHOW)) {
				try {
					quiz.viewQuizQuestion();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (choice.equals(START)) {
				try {
					quiz.playQuizGame();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (choice.equals(END)) {
				flag = false;
			} else {
				System.out.println("잘못된 접근입니다.");
			}

		}

	} // end of main

} // end of class
