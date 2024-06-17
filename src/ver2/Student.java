package ver2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student implements Sql, Choice {

	public static void main(String[] args) {

		while (true) {

			menu();

			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();

			if (choice.equals(ADD)) {
				insertUser(sc);
			} else if (choice.equals(VIEW)) {
				viewInfo();
			} else if (choice.equals(UPDATE)) {
				updateInfo(sc);
			} else if (choice.equals(DELETE)) {
				deleteInfo(sc);
			} else if (choice.equals(END)) {
				System.out.println("종료합니다.");
				break;
			} else {
				System.out.println("잘못된 선택입니다.");
			}
		}

	} // end of main

	// 메뉴
	private static void menu() {
		System.out.println("---------------");
		System.out.println("1.학생 정보 추가");
		System.out.println("2.학생 정보 전체 조회");
		System.out.println("3.학생 정보 수정");
		System.out.println("4.학생 정보 삭제");
		System.out.println("5.종료");
		System.out.println("---------------");
	}
	
	// 학생 정보 추가
	private static void insertUser(Scanner sc) {
		try {
			Connection conn = DBConnectionManager.getConnection();
			System.out.println("회원가입을 실시합니다.");
			System.out.println("이름 : ");
			String userName = sc.nextLine();

			System.out.println("나이 : ");
			String userAge = sc.nextLine();
			int checkAge = Integer.parseInt(userAge);

			if (isNumberic(userAge)) {

				if (checkAge <= 130) {
					System.out.println("이메일 : ");
					String userEmail = sc.nextLine();

					if (authenticateUser(conn, userEmail)) {
						System.out.println("이메일이 중복됩니다.");
					} else {
						PreparedStatement pstmt = conn.prepareStatement(insertSql);
						pstmt.setString(1, userName);
						pstmt.setString(2, userAge);
						pstmt.setString(3, userEmail);
						pstmt.executeUpdate();
						System.out.println("회원가입 성공");
					}
				}else {
					System.out.println("130세까지만 입력이 가능합니다.");
				}

			} else {
				System.out.println("나이란에는 숫자만 입력해주세요.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 숫자인지 확인
	public static boolean isNumberic(String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}

	// 학생 정보 추가시 이메일 중복 확인
	private static boolean authenticateUser(Connection conn, String email) {
		String sameEmail = "  select * from students where email = ?  ";

		boolean result = false;

		try {
			// 이메일 중복 확인
			PreparedStatement pstmt = conn.prepareStatement(sameEmail);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 학생 정보 조회
	private static void viewInfo() {
		List<StudentDTO> list = new ArrayList<>();

		try (Connection conn = DBConnectionManager.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(selectSql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String email = rs.getString("email");
				list.add(new StudentDTO(id, name, age, email));
				if (!rs.isLast()) {
					System.out.println("------------------------");
				}
			}

			for (StudentDTO studentDTO : list) {
				System.out.println(studentDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 학생 이름 정보 수정
	private static void updateInfo(Scanner sc) {
		try (Connection conn = DBConnectionManager.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(updateSql);
			System.out.println("바꾸고 싶은 이름을 입력하세요.");
			String name = sc.nextLine();

			System.out.println("가입했던 이메일을 입력하세요.");
			String email = sc.nextLine();

			List<StudentDTO> list = new ArrayList<>();
			PreparedStatement pstmt2 = conn.prepareStatement(selectSql);
			ResultSet rs = pstmt2.executeQuery();
			while (rs.next()) {
				String originEmail = rs.getString("email");
				if (email.equals(originEmail)) {
					pstmt.setString(1, name);
					pstmt.setString(2, email);
					pstmt.executeUpdate();
					System.out.println("수정 성공");
				} else {
					System.out.println("존재하지 않는 이메일입니다.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 학생 정보 삭제
	private static void deleteInfo(Scanner sc) {

		try (Connection conn = DBConnectionManager.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(deleteSql);
			System.out.println("삭제할 계정의 이메일을 입력하세요.");
			String delete = sc.nextLine();

			List<StudentDTO> list = new ArrayList<>();
			PreparedStatement pstmt2 = conn.prepareStatement(selectSql);
			ResultSet rs = pstmt2.executeQuery();

			while (rs.next()) {
				String originEmail = rs.getString("email");
				if (delete.equals(originEmail)) {
					pstmt.setString(1, delete);
					pstmt.executeUpdate();
					System.out.println("삭제 성공");
				} else {
					System.out.println("존재하지 않는 이메일입니다.");
				}
			}

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} // end of class
