package ver2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ver2.model.StudentDTO;

// 물론 기능 설계는 인터페이스를 먼저 작성하고 구현클래스를 만드는 것이 좋다.
public class StudentDAO implements StudentDAOImpl {

	// 학생 정보 추가 기능 만들기
	// String name, int age, String email = StudentDTO dto
	// StudentDTO dto <-- 클래스는 데이터의 묶음으로 바라보아도 된다.
	public void addStudent(StudentDTO dto) throws SQLException {
		String query = "  INSERT INTO students(name,age,email) VALUES(?, ?, ?)  ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getAge());
			pstmt.setString(3, dto.getEmail());
			pstmt.executeUpdate();
		}
		// catch 삭제 : throws 위에 적어줬으니까.
	}

	// 학생 전체 조회 기능
	public List<StudentDTO> getAllStudents() throws SQLException {
		// tip - List라면 무조건 리스트를 생성하고 코드 작성하세요!!
		List<StudentDTO> list = new ArrayList<>(); // 다른 거 들어와도 되지만 가장 만만한 ArrayList

		String query = "  SELECT * FROM students WHERE id = ?  ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				StudentDTO dto = new StudentDTO().builder().id(rs.getInt("id")).name(rs.getString("name"))
						.age(rs.getInt("age")).email(rs.getString("email")).build();

				list.add(dto);
			}

		}

		// TODO 수정하기
		return list;
	}

	// 학생 아이디로 조회하는 기능 만들기(id)
	public StudentDTO getStudentById(int id) throws SQLException {

		String query = " SELECT * FROM students WHERE id = ? ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				// ResultSet 자원 자동으로 닫기 위해 try 괄호 안에 넣어준다.
				if (rs.next()) {
					return new StudentDTO(rs.getInt("id"), rs.getString("name"), rs.getInt("age"),
							rs.getString("email"));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		// TODO 수정하기
		return null;
	}

	// 학생 정보 수정하는 기능
	public void updateStudent(String name, StudentDTO dto) throws SQLException {
		String query = " UPDATE students SET name = ?, age = ?, email = ? WHERE name = ? ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getAge());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, name); // 조건값 셋팅
			pstmt.executeUpdate();
		} catch (Exception e) {

		}

	}

	// 학생 정보 삭제하기
	public void deleteStudent(int id) throws SQLException {

		String query = " DELETE FROM students WHERE id = ? ";

		try (Connection conn = DBConnectionManager.getInstance().getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {

		}

	}

} // end of class