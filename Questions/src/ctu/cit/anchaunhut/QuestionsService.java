package ctu.cit.anchaunhut;

import java.io.StringReader;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class QuestionsService {

	private static db db = new db();

	public String addQuestion(Questions question) throws ClassNotFoundException {

		String sql = "INSERT INTO Questions (question_text, quiz_id) VALUES (?, ?)";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = db.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, question.getQuestion_text());
			preparedStatement.setString(2, question.getQuiz_id());
			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {

				// Get ID of new Question by PreparedStatement
				generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int newQuestionId = generatedKeys.getInt(1);
					connection.close();

					// Add new SUCCESS - Return Question_id
					return newQuestionId + "";
				} else {
					return "addQuestion - Failed to retrieve the ID of the new QUESTION.";
				}
			} else {
				return "addQuestion - Failed to add new QUESTION.";
			}
		} catch (SQLException e) {
			return "addQuestion - PreparedStatement QUESTION not work !!! : " + e;
		} finally {
			if (generatedKeys != null) {
				try {
					generatedKeys.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String readQuestion(String question_id) throws ClassNotFoundException, SQLException {

		Questions question = null;
		String sql = "SELECT * FROM Questions WHERE question_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, question_id);
		try (ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				question = new Questions();
				question.setQuestion_id(resultSet.getString("question_id"));
				question.setQuestion_text(resultSet.getString("question_text"));
				question.setQuiz_id(resultSet.getString("quiz_id"));
			}

		} catch (Exception e) {
			System.out.println("readQuestion - Resultset Don't work !!! :" + e);
		}
		connection.close();

		return question.readWithOptions(question_id);

	}

	// Delete All Option First ( Don't call Option Service )
	// Then delete Question by question_id
	public String deleteQuestion(String question_id) {

		if (question_id.compareTo("") == 0) {
			return "No have question_id";
		}
		String sql = "DELETE FROM Questions WHERE question_id = ?";
		String sqlDeleteOptions = "DELETE FROM Options WHERE question_id = ?";

		Connection connection = null;

		try {
			connection = db.getConnection();

//			Delete all options with questions_id
			PreparedStatement statementDeleteOptions = connection.prepareStatement(sqlDeleteOptions);
			statementDeleteOptions.setString(1, question_id);
			statementDeleteOptions.executeUpdate();

//			Delete question
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, question_id);
			statement.executeUpdate();

		} catch (ClassNotFoundException e) {
			return "deleteQuestion -Can't create connection to db in \"deleteQuestion\"";
		} catch (SQLException e) {
			return "deleteQuestion - PreparedStatement in deleteQuestion not work !!! : " + e;

		}

		return "Delete \"QUESTION\" Success ";
	}

	public String delete_All_Question_With_QuizID(String quiz_id) throws ClassNotFoundException, SQLException {

		String sql = "SELECT * FROM Questions WHERE quiz_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		try {
			statement.setString(1, quiz_id);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				this.deleteQuestion(resultSet.getString("question_id"));
			}
		} catch (Exception e) {
			System.out.println("Resultset Don't work !!! :" + e);
		}

		return "Delete All Question with Quiz_id SUCCESS";

	}

//	This is call API of Options - return String JSON type
	protected static String read_All_Option_With_Question_Id(String question_id) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Z_Options/api/options/readAll").build();

		WebTarget target = client.target(uri);

		String response = target.queryParam("question_id", question_id).request().accept(MediaType.APPLICATION_JSON)
				.get(String.class);

		return response;
	}

	public String readAllQuestionsWithQuizId(String quiz_id) throws SQLException, ClassNotFoundException {

		List<String> listQuestion = new ArrayList<>();

		String sql = "SELECT * FROM Questions WHERE quiz_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, quiz_id);

		try (ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				listQuestion.add(this.readQuestion(resultSet.getString("question_id")));
			}

		} catch (Exception e) {
			System.out.println("Resultset Don't work !!! :" + e);
		}
		connection.close();

		return listQuestion.toString();
	}

//	Uanser is "ID OPTION " id choose for "ID QUESTION"
	public String checkCorrectQuestion(String question_id, String Uanswer) throws ClassNotFoundException, SQLException {

		String dataQuestion = readQuestion(question_id);

		// Create a StringReader object
		StringReader stringReader = new StringReader(dataQuestion);

		// Create a JsonReader object from StringReader
		JsonReader jsonReader = Json.createReader(stringReader);

		// Get Question OBJ from JsonReader
		JsonObject jsonQuestion = jsonReader.readObject();

		// Get Options data - contatin array of multiple option
		JsonArray jsonOptions = jsonQuestion.getJsonArray("options");

		// Loop each option.
		for (int i = 0; i < jsonOptions.size(); i++) {

			// Get option
			JsonObject jsonOption = jsonOptions.getJsonObject(i);

			// Check if option is_correct = true, continue check if "User Answer" = this
			// option => Return True
			if (jsonOption.getBoolean("is_correct") == true) {
				if (jsonOption.getString("option_id").compareTo(Uanswer) == 0) {
					return "true";
				}
			}

		}

		return "false";
	}

}
