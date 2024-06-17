package ver3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizDTO {

	// data transfer object
	// mysql 한 줄에 해당하는 데이터를 담는 용도로 설계할 것이다.
	private int id;
	private String question;
	private String answer;

}
