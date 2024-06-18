package ver2;

import java.sql.SQLException;
import java.util.List;

import ver2.model.StudentDTO;

public interface StudentDAOImpl {

	// 학생 정보 추가
	public void addStudent(StudentDTO dto) throws SQLException;

	// 학생 전체 조회
	public List<StudentDTO> getAllStudents() throws SQLException;

	// 학생 아이디로 조회
	public StudentDTO getStudentById(int id) throws SQLException;

	// 학생 정보 수정
	public void updateStudent(String name, StudentDTO dto) throws SQLException;

	// 학생 정보 삭제
	public void deleteStudent(int id) throws SQLException;

}
