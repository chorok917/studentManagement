package ver1;

public interface Sql {
	// sql 구문 CRUD
		public static final String insertSql = "  INSERT INTO students(name,age,email) VALUES(?,?,?) ";
		public static final String selectSql = "  SELECT * FROM students  ";
		public static final String updateSql = "  UPDATE students SET name = ? WHERE email = ?  ";
		public static final String deleteSql = "  DELETE FROM students WHERE email = ?  ";
}
