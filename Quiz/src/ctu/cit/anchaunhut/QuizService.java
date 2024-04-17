package ctu.cit.anchaunhut;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class QuizService {

	private static db db = new db();

	public String addNewQuiz(Quiz quiz) throws ClassNotFoundException {

		String sql = "INSERT INTO Quizzes (quiz_title, quiz_description, creator_id) VALUES (?, ?, ?)";

		Connection connection = db.getConnection();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, quiz.getQuiz_title());
			preparedStatement.setString(2, quiz.getQuiz_description());
			preparedStatement.setString(3, quiz.getCreator_id());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				connection.close();
				return "A new \\ Quiz \\ was added successfully.";
			} else {
				return "Failed to add new \\ Quiz \\.";
			}
		} catch (SQLException e) {
			return "PreparedStatement \\ Quiz \\ not work !!! : " + e;
		}

	}

	public String readQuiz(String quiz_id) throws ClassNotFoundException, SQLException {

		Quiz question = null;
		String sql = "SELECT * FROM Quizzes WHERE quiz_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, quiz_id);
		try (ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				question = new Quiz();
				question.setQuiz_id(resultSet.getString("quiz_id"));
				question.setQuiz_title(resultSet.getString("quiz_title"));
				question.setQuiz_description(resultSet.getString("quiz_description"));
				question.setCreator_id(resultSet.getString("creator_id"));
				question.setCreated_at(resultSet.getDate("created_at"));
			}

		} catch (Exception e) {
			System.out.println("Resultset Don't work in READ QUIZ !!! :" + e);
		}
		connection.close();
		return question.toString_with_QuizID(quiz_id);

	}

	public String readAllQuiz() throws SQLException, ClassNotFoundException {

		String sql = "SELECT * FROM Quizzes";

		List<Quiz> listQuiz = new ArrayList<>();
		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Quiz quiz = new Quiz();
				quiz.setQuiz_id(resultSet.getString("quiz_id"));
				quiz.setQuiz_title(resultSet.getString("quiz_title"));
				quiz.setQuiz_description(resultSet.getString("quiz_description"));
				quiz.setCreator_id(resultSet.getString("creator_id"));
				quiz.setCreated_at(resultSet.getDate("created_at"));
				listQuiz.add(quiz);
			}

		} catch (Exception e) {
			System.out.println("Resultset Don't work in READ QUIZ !!! :" + e);
		}
		connection.close();
		return listQuiz.toString();

	}
//	public String updateQuiz(Quiz quiz) throws SQLException, ClassNotFoundException {
//
//		String sql = "UPDATE Quizzes SET quiz_title = ?, quiz_description = ? WHERE quiz_id = ?";
//
//		Connection connection = db.getConnection();
//		PreparedStatement statement = connection.prepareStatement(sql);
//
//		try {
//
//			connection = db.getConnection();
//
//			statement.setString(1, quiz.getQuiz_title());
//			statement.setString(2, quiz.getQuiz_description());
//			statement.setString(3, quiz.getQuiz_id());
//
//			statement.executeUpdate();
//		} catch (ClassNotFoundException e) {
//			System.out.println("Can't create connection to db in \"updateQUIZ \" + : " + e);
//		}
//		return "update \"QUIZ\" success ";
//	}

	
	// Call Service Question - delete_All_Question_With_QuizID() first
	// Then Delete Quiz
	public String deleteQuiz(String quiz_id) {


		this.call_Delete_All_Question_First(quiz_id);
		
		String sql = "DELETE FROM Quizzes WHERE quiz_id = ?";

		Connection connection = null;

		try {
			connection = db.getConnection();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, quiz_id);
			statement.executeUpdate();

		} catch (ClassNotFoundException e) {
			return "Can't create connection to db in \"deleteQuiz\"";
		} catch (SQLException e) {
			return "PreparedStatement in deleteQuiz not work !!! : " + e;

		}

		return "DELETE_Quiz_SUCCESS";
//		return this.call_Delete_All_Question_First(quiz_id);
	}
	
	private String call_Delete_All_Question_First(String quiz_id) {
		
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		URI uri = UriBuilder.fromUri(
				"http://localhost:8080/Questions/api/questions/deleteWithQuizId").build();
		
		WebTarget target = client.target(uri);
		

		String response = 
				target.queryParam("quiz_id", quiz_id)
				.request().accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		return response;
	}

	protected static String read_All_Question_With_Quiz_id(String quiz_id) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Questions/api/questions/readAll").build();

		WebTarget target = client.target(uri);

		String response = target.queryParam("quiz_id", quiz_id).request().accept(MediaType.APPLICATION_JSON)
				.get(String.class);

		return response;
	}
	

}
