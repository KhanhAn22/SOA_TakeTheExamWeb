package ctu.cit.anchaunhut;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionsService {

	private static db db = new db();

	public String addOption(Options options) throws ClassNotFoundException {

		String sql = "INSERT INTO Options (question_id, option_text, option_image, is_correct) VALUES (?, ?, ?, ?)";

		Connection connection = db.getConnection();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, options.getQuestion_id());
			preparedStatement.setString(2, options.getOption_text());
			preparedStatement.setString(3, options.getOption_image());
			preparedStatement.setBoolean(4, options.isIs_correct());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				connection.close();
				return "A new option was added successfully.";
			} else {
				return "Failed to add new option.";
			}
		} catch (SQLException e) {
			return "PreparedStatement not work !!! : " + e;
		}

	}

	public String readOption(String options_id) throws ClassNotFoundException, SQLException {

		Options option = null;
		String sql = "SELECT * FROM Options WHERE option_id = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, options_id);
		try (ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				option = new Options();
				option.setQuestion_id(resultSet.getString("question_id"));
				option.setOption_text(resultSet.getString("option_text"));
				option.setOption_image(resultSet.getString("option_image"));
				option.setIs_correct(resultSet.getBoolean("is_correct"));
			}
		} catch (Exception e) {
			System.out.println("Resultset Don't work !!!");
		}
		connection.close();
		return option.toString();

	}

	public String readAllOptionByQuestionId(String question_id) throws ClassNotFoundException, SQLException {

		String sql = "SELECT * FROM Options WHERE question_id = ?";
		List<Options> optionsList = new ArrayList<>();
		ResultSet resultSet = null;
		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, question_id);
		try {
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Options option = new Options();
				option.setQuestion_id(resultSet.getString("question_id"));
				option.setOption_text(resultSet.getString("option_text"));
				option.setOption_image(resultSet.getString("option_image"));
				option.setIs_correct(resultSet.getBoolean("is_correct"));
				option.setOption_id(resultSet.getString("option_id"));

				
				optionsList.add(option);
			}
		} catch (Exception e) {
			System.out.println("Resultset Don't work !!!");
		} finally {
			// Close the resources in a finally block to ensure they are always closed
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
		}
		return convertOptionsListToJson(optionsList);

	}

	public String convertOptionsListToJson(List<Options> optionsList) {
		StringBuilder jsonBuilder = new StringBuilder();
		jsonBuilder.append("["); // Start of JSON array

		// Iterate through the optionsList
		for (int i = 0; i < optionsList.size(); i++) {
			Options option = optionsList.get(i);

			// Append JSON object for each option
			jsonBuilder.append("{").append("\"question_id\":\"").append(option.getQuestion_id()).append("\",")
					.append("\"option_text\":\"").append(option.getOption_text()).append("\",")
					.append("\"option_id\":\"").append(option.getOption_id()).append("\",")
					.append("\"is_correct\":").append(option.isIs_correct())
					.append("}");
//					.append("\"option_image\":\"").append(option.getOption_image()).append("\",")

			// Add comma if not the last element
			if (i < optionsList.size() - 1) {
				jsonBuilder.append(",");
			}
		}

		jsonBuilder.append("]"); // End of JSON array
		return jsonBuilder.toString();
	}

//	public String updateOption(Options option) throws SQLException {
//		String sql = "UPDATE Options SET question_id = ?, option_text = ?, option_image = ?, is_correct = ? WHERE option_id = ?";
//		Connection connection = null;
//		try {
//			connection = db.getConnection();
//		} catch (ClassNotFoundException e) {
//			System.out.println("Can't create connection to db in \"OPTIONSERVICE\"");
//		}
//		try (PreparedStatement statement = connection.prepareStatement(sql)) {
//			statement.setString(1, option.getQuestion_id());
//			statement.setString(2, option.getOption_text());
//			statement.setString(3, option.getOption_image());
//			statement.setBoolean(4, option.isIs_correct());
//			statement.setString(5, option.getOption_id());
//			statement.executeUpdate();
//		}
//		return "update \"OPTION\" success ";
//	}

	public String deleteOption(String option_id) {
		String sql = "DELETE FROM Options WHERE option_id = ?";

		Connection connection = null;

		try {
			connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, option_id);
			statement.executeUpdate();

		} catch (ClassNotFoundException e) {
			System.out.println("Can't create connection to db in \"OPTIONSERVICE\"");
		} catch (SQLException e) {
			System.out.println("PreparedStatement in deleteOption not work !!!");
		}

		return "Delete \"OPTION\" Success ";
	}
}
