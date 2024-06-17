package ver3;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class MainTest1 {

	public static void main(String[] args) {

		QuizRepositoryImpl quizRepositoryImpl = new QuizRepositoryImpl();

		try {
//			List<QuizDTO> quizDtos = quizRepositoryImpl.viewQuizQuestion();
//			for (QuizDTO quizDTO : quizDtos) {
//				System.out.println(quizDTO);
//			}

			QuizDTO dto = quizRepositoryImpl.playQuizGame();
			System.out.println(dto);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	} // end of main

} // end of class
