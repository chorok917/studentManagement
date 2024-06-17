package ver2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public interface QuizRepository {

	int addQuizQuestion(String question, String answer) throws SQLException;
	List<QuizDTO> viewQuizQuestion() throws SQLException;
	QuizDTO playQuizGame() throws SQLException;
	
} // end of class