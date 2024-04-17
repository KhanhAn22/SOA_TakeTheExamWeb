package ctu.cit.anchaunhut;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswersService {

	private static db db = new db();

	public String addNewAnswers(Answers answer) throws ClassNotFoundException {

		String sql = "INSERT INTO Answers (session_id, question_id, user_answer, is_correct) VALUES (?, ?, ?, ?)";

		Connection connection = db.getConnection();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, answer.getSession_id());
			preparedStatement.setString(2, answer.getQuestion_id());
			preparedStatement.setString(3, answer.getUser_answer());
			preparedStatement.setBoolean(4, answer.getIs_correct());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				connection.close();
				return "A new \\ addAnswer \\ was added successfully.";
			} else {
				return "Failed to add new \\\\ addAnswer \\\\.";
			}
		} catch (SQLException e) {
			return "PreparedStatement \\\\ addAnswer \\\\ not work !!! : " + e;
		}

	}

	public Answers readAnswers(String answer_id) throws ClassNotFoundException, SQLException {

		Answers answer = null;
		String sql = "SELECT * FROM Answers WHERE answer_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, answer_id);
		try (ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				answer = new Answers();
				answer.setAnswer_id(resultSet.getString("answer_id"));
				answer.setSession_id(resultSet.getString("session_id"));
				answer.setQuestion_id(resultSet.getString("question_id"));
				answer.setUser_answer(resultSet.getString("user_answer"));
				answer.setIs_correct(resultSet.getBoolean("is_correct"));
			}

		} catch (Exception e) {
			System.out.println("Resultset Don't work !!! :" + e);
		}
		connection.close();

		return answer;

	}
	
	public String list_All_Answer_With_SessionId(String session_id) throws SQLException, ClassNotFoundException{
		
		List<Answers> listQuestion = new ArrayList<>();
		

		String sql = "SELECT * FROM Answers WHERE session_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, session_id);
		
		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				listQuestion.add(this.readAnswers(resultSet.getString("answer_id")));
			}

		} catch (Exception e) {
			System.out.println("Resultset Don't work !!! :" + e);
		}
		connection.close(); 
		
		return listQuestion.toString();
	}

}
