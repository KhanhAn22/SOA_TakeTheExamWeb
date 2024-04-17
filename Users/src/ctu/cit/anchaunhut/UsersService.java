package ctu.cit.anchaunhut;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersService {

	private static db db = new db();

	public String addUsers(Users user) throws ClassNotFoundException {

		String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

		Connection connection = db.getConnection();
		PreparedStatement preparedStatement;

		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassWord());
			preparedStatement.setString(3, user.getEmail());

			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				connection.close();
				return "A new \\ USER \\ was added successfully.";
			} else {
				return "Failed to add new \\\\ USER \\\\.";
			}
		} catch (SQLException e) {
			return "PreparedStatement \\\\ USER \\\\ not work !!! : " + e;
		}

	}

	public String authentication(Users user) throws SQLException, ClassNotFoundException {

		Users userOutput = null;
		String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

		Connection connection = db.getConnection();

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, user.getUserName());
		statement.setString(2, user.getPassWord());

		try (ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				userOutput = new Users();

				userOutput.setUser_id(resultSet.getString("user_id"));
				userOutput.setUserName(resultSet.getString("username"));
				userOutput.setPassWord(resultSet.getString("password"));
				userOutput.setEmail(resultSet.getString("email"));

			} else {
				return "LoginFail";
			}

		} catch (Exception e) {
			System.out.println("authentication - Resultset Don't work !!! :" + e);
		}
		connection.close();

		if(userOutput.getUser_id().compareTo("1") == 0) {
			return "adminLogin";
		}
		return userOutput.toString();
	}
}
