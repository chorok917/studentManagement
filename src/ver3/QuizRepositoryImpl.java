package ver3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QuizRepositoryImpl implements QuizRepository {

	public static final String insertSql = "insert into quiz(question,answer) values(?,?) ";
	public static final String selectSql = "  select * from quiz  ";
	public static final String randomSql = "  select * from quiz order by rand() limit 1  ";

	@Override
	public int addQuizQuestion(String question, String answer) throws SQLException {
		int resultRowCount = 0; // 초기화 해주는 습관
		try (Connection conn = DBConnectionManager.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(insertSql);
			// 트랜잭션 처리 가능 - 수동 모드 커밋 사용 가능
			pstmt.setString(1, question);
			pstmt.setString(2, answer);
			pstmt.executeUpdate();
		}

		return resultRowCount;
	}

	@Override
	public List<QuizDTO> viewQuizQuestion() throws SQLException {

		List<QuizDTO> list = new ArrayList<>();

		try (Connection conn = DBConnectionManager.getConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(selectSql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String question = rs.getString("question");
				String answer = rs.getString("answer");
				list.add(new QuizDTO(id, question, answer)); // 익명 클래스
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public QuizDTO playQuizGame() throws SQLException {

		QuizDTO quizDTO = null;

		try (Connection conn = DBConnectionManager.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(randomSql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String question = rs.getString("question");
				String answer = rs.getString("answer");
				quizDTO = new QuizDTO(id, question, answer);
			}
		}

		return quizDTO;
	}

} // end of class
