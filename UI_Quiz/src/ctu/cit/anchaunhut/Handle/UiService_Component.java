package ctu.cit.anchaunhut.Handle;

import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import ctu.cit.anchaunhut.Controller.UIServiceController;


public class UiService_Component {


	public void takeTheExam(String quiz_id, String user_id, PrintWriter out) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Quiz/api/quiz/read").build();

		WebTarget target = client.target(uri);

		// Receive the response as bytes instead of String
		byte[] responseBytes = target.queryParam("quiz_id", quiz_id).request().accept(MediaType.APPLICATION_JSON)
				.get(byte[].class);

		// Convert the response bytes to a String using UTF-8 encoding
		String jsonResponse = new String(responseBytes, StandardCharsets.UTF_8);
		System.out.println("takeTheExam : Received JSON Response !!!");
//		Convert obj Quiz to HTML
		convertQuizToHTML(out, jsonResponse, user_id);
	}

	// Call API ReadAll of practiceSession to return List of Session by user_id and
	// quiz_id is parameter.
	public void getAllSessionByQuiz_id(PrintWriter out, String user_id, String quiz_id) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Practice_Sessions/api/practiceSessions/readAll").build();

		WebTarget target = client.target(uri);

		String response = target.queryParam("quiz_id", quiz_id).queryParam("user_id", user_id).request()
				.accept(MediaType.APPLICATION_JSON).get(String.class);

		// Call function handle JSON LIST SESSION to HTML.
		convertListSessionToHTML(out, response);
	}

	// Call API Create Session in PracticeSession
	public String StartSessionExam(String user_id, String quiz_id) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Practice_Sessions/api/practiceSessions/new").build();

		WebTarget target = client.target(uri);

		// Create a JSON object representing your request data
		JsonObject requestData = Json.createObjectBuilder().add("quiz_id", quiz_id).add("user_id", user_id).build();

		// Send a POST request with the JSON data
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(requestData));

		// Read the response body
		String jsonResponse = response.readEntity(String.class);

		// Close the response
		response.close();
		return jsonResponse.toString();

	}

	public void GetQuestionById(PrintWriter out, String question_id, Boolean is_correct, String user_answer) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Questions/api/questions/read").build();

		WebTarget target = client.target(uri);

		String response = target.queryParam("question_id", question_id).request().accept(MediaType.APPLICATION_JSON)
				.get(String.class);

		// Start out.println() data Question to UI
		System.out.println("GetQuestionById - Start convert data Question to HTML");
		convertQuestionToHTML(out, response, is_correct, user_answer);
	}
	
	
	public void getAllQuiz(PrintWriter out, String user_id) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Quiz/api/quiz/readAllQuiz").build();

		WebTarget target = client.target(uri);

		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);

		// Convert data String to HTML
		convertListQuizToHTML(out, response, user_id);
	}
	private void convertListQuizToHTML(PrintWriter out, String listQuizString, String user_id) {
		UIServiceController UiService = new UIServiceController();

		// Convert dataQuiz ( contain question + option )
		JsonReader jsonReader = Json.createReader(new StringReader(listQuizString));
		JsonArray quizArray = jsonReader.readArray();

		for (int i = 0; i < quizArray.size(); i++) {
			JsonObject quizObj = quizArray.getJsonObject(i);

			String quiz_id = quizObj.getString("quiz_id");
			String quiz_title = quizObj.getString("quiz_title");
			String quiz_description = quizObj.getString("quiz_description");
//			String creator_id = quizObj.getString("creator_id");
			String created_at = quizObj.getString("created_at");

			//// Start <div> contain each Quiz
			out.println("<div id=\"quiz" + quiz_id
					+ "\" style='border: 1px solid black; padding: 10px; margin-bottom: 10px;'>");

			// Output quiz data======================================================
			out.println("<p><strong>Title:</strong> " + quiz_title + "</p>");
			out.println("<p><strong>Description:</strong> " + quiz_description + "</p>");
			out.println("<p><strong>Created At:</strong> " + created_at + "</p>");
			// Output quiz data======================================================

			// FORM Take the exam====================================================================
			out.println("<form method=\"POST\" action=\"/UI_Quiz/takeTheExam\" >");
			
			out.println("<input type=\"hidden\" name=\"quiz_id\" value=\""+ quiz_id +"\">");
			out.println("<input type=\"hidden\" name=\"user_id\" value=\""+ user_id +"\">");


			out.println("<button>Take the Exam</button>");
			out.println("</form>");
			// FORM Take the exam====================================================================

			// Start <div> contain session of Quiz
			out.println("<div id=\"session_with_quiz_id\" >");
			
			UiService.getAllSessionByQuiz_id(out, user_id, quiz_id);

			// End <div> contain session of Quiz
			out.println("</div>");
			
			//// End <div> contain each Quiz
			out.println("</div>");
		}
	}

	// All CSS OF PAGE

	public void cssLogin(PrintWriter out) {

		out.println("<style>");
		out.println(
				"@import url('https://fonts.googleapis.com/css2?family=Quicksand:wght@300;400;500;600;700&display=swap');\r\n"
						+ "*\r\n" + "{\r\n" + "  margin: 0;\r\n" + "  padding: 0;\r\n" + "  box-sizing: border-box;\r\n"
						+ "  font-family: 'Quicksand', sans-serif;\r\n" + "}\r\n" + "body \r\n" + "{\r\n"
						+ "  display: flex;\r\n" + "  justify-content: center;\r\n" + "  align-items: center;\r\n"
						+ "  min-height: 100vh;\r\n" + "  background: #000;\r\n" + "}\r\n" + "section \r\n" + "{\r\n"
						+ "  position: absolute;\r\n" + "  width: 100vw;\r\n" + "  height: 100vh;\r\n"
						+ "  display: flex;\r\n" + "  justify-content: center;\r\n" + "  align-items: center;\r\n"
						+ "  gap: 2px;\r\n" + "  flex-wrap: wrap;\r\n" + "  overflow: hidden;\r\n" + "}\r\n"
						+ "section::before \r\n" + "{\r\n" + "  content: '';\r\n" + "  position: absolute;\r\n"
						+ "  width: 100%;\r\n" + "  height: 100%;\r\n"
						+ "  background: linear-gradient(#000,#0f0,#000);\r\n"
						+ "  animation: animate 5s linear infinite;\r\n" + "}\r\n" + "@keyframes animate \r\n" + "{\r\n"
						+ "  0%\r\n" + "  {\r\n" + "    transform: translateY(-100%);\r\n" + "  }\r\n" + "  100%\r\n"
						+ "  {\r\n" + "    transform: translateY(100%);\r\n" + "  }\r\n" + "}\r\n" + "section span \r\n"
						+ "{\r\n" + "  position: relative;\r\n" + "  display: block;\r\n"
						+ "  width: calc(6.25vw - 2px);\r\n" + "  height: calc(6.25vw - 2px);\r\n"
						+ "  background: #181818;\r\n" + "  z-index: 2;\r\n" + "  transition: 1.5s;\r\n" + "}\r\n"
						+ "section span:hover \r\n" + "{\r\n" + "  background: #0f0;\r\n" + "  transition: 0s;\r\n"
						+ "}\r\n" + "\r\n" + "section .signin\r\n" + "{\r\n" + "  position: absolute;\r\n"
						+ "  width: 400px;\r\n" + "  background: #222;  \r\n" + "  z-index: 1000;\r\n"
						+ "  display: flex;\r\n" + "  justify-content: center;\r\n" + "  align-items: center;\r\n"
						+ "  padding: 40px;\r\n" + "  border-radius: 4px;\r\n"
						+ "  box-shadow: 0 15px 35px rgba(0,0,0,9);\r\n" + "}\r\n" + "section .signin .content \r\n"
						+ "{\r\n" + "  position: relative;\r\n" + "  width: 100%;\r\n" + "  display: flex;\r\n"
						+ "  justify-content: center;\r\n" + "  align-items: center;\r\n"
						+ "  flex-direction: column;\r\n" + "  gap: 40px;\r\n" + "}\r\n"
						+ "section .signin .content h2 \r\n" + "{\r\n" + "  font-size: 2em;\r\n" + "  color: #0f0;\r\n"
						+ "  text-transform: uppercase;\r\n" + "}\r\n" + "section .signin .content .form \r\n" + "{\r\n"
						+ "  width: 100%;\r\n" + "  display: flex;\r\n" + "  flex-direction: column;\r\n"
						+ "  gap: 25px;\r\n" + "}\r\n" + "section .signin .content .form .inputBox\r\n" + "{\r\n"
						+ "  position: relative;\r\n" + "  width: 100%;\r\n" + "}\r\n"
						+ "section .signin .content .form .inputBox input \r\n" + "{\r\n" + "  position: relative;\r\n"
						+ "  width: 100%;\r\n" + "  background: #333;\r\n" + "  border: none;\r\n"
						+ "  outline: none;\r\n" + "  padding: 25px 10px 7.5px;\r\n" + "  border-radius: 4px;\r\n"
						+ "  color: #fff;\r\n" + "  font-weight: 500;\r\n" + "  font-size: 1em;\r\n" + "}\r\n"
						+ "section .signin .content .form .inputBox i \r\n" + "{\r\n" + "  position: absolute;\r\n"
						+ "  left: 0;\r\n" + "  padding: 15px 10px;\r\n" + "  font-style: normal;\r\n"
						+ "  color: #aaa;\r\n" + "  transition: 0.5s;\r\n" + "  pointer-events: none;\r\n" + "}\r\n"
						+ ".signin .content .form .inputBox input:focus ~ i,\r\n"
						+ ".signin .content .form .inputBox input:valid ~ i\r\n" + "{\r\n"
						+ "  transform: translateY(-7.5px);\r\n" + "  font-size: 0.8em;\r\n" + "  color: #fff;\r\n"
						+ "}\r\n" + ".signin .content .form .links \r\n" + "{\r\n" + "  position: relative;\r\n"
						+ "  width: 100%;\r\n" + "  display: flex;\r\n" + "  justify-content: space-between;\r\n"
						+ "}\r\n" + ".signin .content .form .links a \r\n" + "{\r\n" + "  color: #fff;\r\n"
						+ "  text-decoration: none;\r\n" + "}\r\n" + ".signin .content .form .links a:nth-child(2)\r\n"
						+ "{\r\n" + "  color: #0f0;\r\n" + "  font-weight: 600;\r\n" + "}\r\n"
						+ ".signin .content .form .inputBox input[type=\"submit\"]\r\n" + "{\r\n"
						+ "  padding: 10px;\r\n" + "  background: #0f0;\r\n" + "  color: #000;\r\n"
						+ "  font-weight: 600;\r\n" + "  font-size: 1.35em;\r\n" + "  letter-spacing: 0.05em;\r\n"
						+ "  cursor: pointer;\r\n" + "}\r\n" + "input[type=\"submit\"]:active\r\n" + "{\r\n"
						+ "  opacity: 0.6;\r\n" + "}\r\n" + "@media (max-width: 900px)\r\n" + "{\r\n"
						+ "  section span \r\n" + "  {\r\n" + "    width: calc(10vw - 2px);\r\n"
						+ "    height: calc(10vw - 2px);\r\n" + "  }\r\n" + "}\r\n" + "@media (max-width: 600px)\r\n"
						+ "{\r\n" + "  section span \r\n" + "  {\r\n" + "    width: calc(20vw - 2px);\r\n"
						+ "    height: calc(20vw - 2px);\r\n" + "  }\r\n" + "}");
		out.println("</style>");
	}

	public void css(PrintWriter out) {
		out.println("<style>");

		out.println("h3 {\r\n" + "		    text-align: center; /* Center the text */\r\n"
				+ "		    font-family: Arial, sans-serif; /* Choose a font */\r\n"
				+ "		    color: #333; /* Text color */\r\n"
				+ "		    background-color: #f0f0f0; /* Background color */\r\n"
				+ "		    padding: 10px; /* Add padding */\r\n"
				+ "		    border-radius: 5px; /* Add border radius for rounded corners */\r\n"
				+ "		    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Add shadow */\r\n"
				+ "		    width: 50%; /* Set width */\r\n"
				+ "		    margin: 0 auto; /* Center horizontally */\r\n" + "		}");

		out.println(".collapsible {\r\n" + "      background-color: #f1f1f1;\r\n" + "      cursor: pointer;\r\n"
				+ "      padding: 18px;\r\n" + "      width: 100%;\r\n" + "      border: none;\r\n"
				+ "      text-align: left;\r\n" + "      outline: none;\r\n" + "      font-size: 15px;\r\n"
				+ "    }\r\n" + "    \r\n" + "    .content {\r\n" + "      padding: 0 18px;\r\n"
				+ "      display: none;\r\n" + "      overflow: hidden;\r\n" + "      background-color: #f1f1f1;\r\n"
				+ "    }");

		// Css for Option indented
		out.println(".option {\r\n" + 
				"    margin-left: 20px;"
				+ "width: fit-content; /* Adjust the value to change the indentation */\r\n" + 
				"}");
		out.println("</style>");

	}
	


	
	public void loginPage(PrintWriter out) {
		out.println("<!doctype html>\r\n" + "\r\n" + "<html lang=\"en\"> \r\n" + "\r\n" + " <head> \r\n" + "\r\n"
				+ "  <meta charset=\"UTF-8\"> \r\n" + "\r\n"
				+ "  <title>Login Page</title> \r\n" + "\r\n"
				+ "  <link rel=\"stylesheet\" href=\"./style.css\"> \r\n" + "\r\n" + " </head> \r\n" + "\r\n"
				+ " <body> <!-- partial:index.partial.html --> \r\n" + "\r\n"
				+ "  <section> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> ");
	}

//	ALL Script OF PAGE
	public void script(PrintWriter out) {
		out.println("<script>\r\n" + "function toggleContent(id) {\r\n"
				+ "  var content = document.getElementById(id);\r\n"
				+ "  if (content.style.display === \"block\") {\r\n" + "    content.style.display = \"none\";\r\n"
				+ "  } else {\r\n" + "    content.style.display = \"block\";\r\n" + "  }\r\n" + "}\r\n" + "</script>");
	}
	
	
	
	
	
	// CONVERT SERVICE
	
	public void convertQuizToHTML(PrintWriter out, String jsonData, String user_id) {

		// Create a StringReader object
		StringReader stringReader = new StringReader(jsonData);

		// Create a JsonReader object from StringReader
		JsonReader jsonReader = Json.createReader(stringReader);

		// Get JsonObject from JsonReader
		JsonObject jsonObject = jsonReader.readObject();

		// Element
		String quiz_id = jsonObject.getString("quiz_id");
		String session_id = StartSessionExam(user_id, quiz_id);

		// Accessing elements
		System.out.println("Start convert JSON data to HTML");

		out.println("<h3>Quiz ID: " + jsonObject.getString("quiz_id") + "</h3>");
		out.println("<h3>Quiz Title: " + jsonObject.getString("quiz_title") + "</h3>");
		out.println("<h3>Quiz Description: " + jsonObject.getString("quiz_description") + "</h3>");
		out.println("<h3>Creator ID: " + jsonObject.getString("creator_id") + "</h3>");
		out.println("<h3>Created At: " + jsonObject.getString("created_at") + "</h3>");

		// Accessing questions

//		Start FORM
		out.println("<form action=\"/UI_Quiz/handle_takeTheExam\" method=\"POST\">");
		JsonArray questionsArray = jsonObject.getJsonArray("question");
		for (int i = 0; i < questionsArray.size(); i++) {

			JsonObject questionObj = questionsArray.getJsonObject(i);
			int questionCount = i + 1;

//			Start <div> element
			out.println("<div>");
			out.println("<p>Question " + questionCount + ": " + questionObj.getString("question_text") + "</p>");
			out.println("<input type=\"hidden\" name=\"user_id\" value=\" " + user_id + "\">");
			out.println("<input type=\"hidden\" name=\"quiz_id\" value=\"" + quiz_id + "\">");
			out.println("<input type=\"hidden\" name=\"session_id\" value= \"" + session_id + "\">");

			String questionId = questionObj.getString("question_id");
			// Accessing options for each question
			JsonArray optionsArray = questionObj.getJsonArray("options");

//			Start div of option
			out.println("<div>");

			for (int j = 0; j < optionsArray.size(); j++) {
				JsonObject optionObj = optionsArray.getJsonObject(j);
				String optionText = optionObj.getString("option_text");
				String optionIdDB = optionObj.getString("option_id");

				String optionId = "option_" + j;

				// Change input type to radio

				out.println("<input type=\"radio\" id=\"" + optionId + "\" name=\"" + questionId + "\" value=\""
						+ optionIdDB + "\" required>");
				out.println("<label for=\"" + optionId + "\">" + optionText + "</label><br>");
			}
//			End div of option
			out.println("</div>");

//			End <div> element
			out.println("</div>");

		}

//		End FORM
		out.println("<input type=\"submit\" value=\"Submit\" />");
		out.println("</form>");
		System.out.println("Convert JSON to HTML DONE");
	}

	public void convertQuestionToHTML(PrintWriter out, String JsonQuestion, Boolean is_correct,
			String user_answer) {

		// Create a StringReader object
		StringReader stringReader = new StringReader(JsonQuestion);

		// Create a JsonReader object from StringReader
		JsonReader jsonReader = Json.createReader(stringReader);

		JsonObject jsonQuestion = jsonReader.readObject();

//		String question_id = jsonQuestion.getString("question_id");
		String question_text = jsonQuestion.getString("question_text");
//		String quiz_id = jsonQuestion.getString("quiz_id");

		if (is_correct) {
			out.println("<h4 style=\"background-color: lawngreen;width: fit-content;\">question_text:" + question_text + "</h4>");
		} else {
			out.println("<h4 style=\"background-color: red;width: fit-content;   \">question_text:" + question_text + "</h4>");

		}

		JsonArray optionsArray = jsonQuestion.getJsonArray("options");

		for (int i = 0; i < optionsArray.size(); i++) {

			JsonObject optionObject = optionsArray.getJsonObject(i);

			String option_text = optionObject.getString("option_text");
//			String option_id = optionObject.getString("option_id");
			Boolean is_correct_draw_green = optionObject.getBoolean("is_correct");

			int user_choose = 0;
			// Draw if this is options user choose
			if (optionObject.getString("option_id").compareTo(user_answer) == 0) {
				user_choose = 1;
			}

			// Draw correct answer for question
			if (is_correct_draw_green) {
				out.println("<p style=\" background-color: green;width: fit-content;\">option_text:" + option_text + "</p>");

			} else {
				if (user_choose == 1) {
					out.println("<p style=\" background-color: yellow;width: fit-content;\">option_text:" + option_text + "</p>");
				} else {
					out.println("<p style=\"width: fit-content;\">option_text:" + option_text + "</p>");
				}
			}

		}

	}

	public String convertListSessionToHTML(PrintWriter out, String jsonData) {

		JsonReader jsonReader = Json.createReader(new StringReader(jsonData));
		JsonArray sessionsArray = jsonReader.readArray();

		for (int i = 0; i < sessionsArray.size(); i++) {
			JsonObject sessionObj = sessionsArray.getJsonObject(i);

			String sessionId = sessionObj.getString("session_id");

			String startDate = sessionObj.getString("start_date");
			
			// Div contains all Element of SESSION
			out.println("<div class=\"collapsible\" onclick=\"toggleContent('" + sessionId + "')\">");

			out.println("Make test: " + startDate);
			
			// CODE HERE - GET_SCORE
			//============================================================================================================
			String score = getScoreOfSession(sessionId);
			out.println(" Score : " + score);
			//============================================================================================================

			out.println("</div>");


			JsonArray answersArray = sessionObj.getJsonArray("answers");
			
			out.println("<div class=\"content\" id=\"" + sessionId + "\">");
			for (int j = 0; j < answersArray.size(); j++) {
				JsonObject answerObj = answersArray.getJsonObject(j);

//				String answerId = answerObj.getString("answer_id");
				String questionId = answerObj.getString("question_id");
				String userAnswer = answerObj.getString("user_answer");
				boolean isCorrect = answerObj.getBoolean("is_correct");

				// Start of <div> contain each Question
				out.println("<div style=\"border-style:solid; padding: 10px;\">");

				// Call API get Question by ID + out.println QUESTION
				GetQuestionById(out, questionId, isCorrect, userAnswer);

				// End of <div> contain each Question
				out.println("</div><br>");

			}
			out.println("</div><br>");
		}

		return "convertSUCCESS";
	}
	
	public String getScoreOfSession(String session_id) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Practice_Sessions/api/practiceSessions/readScore").build();

		WebTarget target = client.target(uri);

		String response = target.queryParam("session_id", session_id).request().accept(MediaType.APPLICATION_JSON)
				.get(String.class);

		return response;
	}
	
	public void cssLoginPage(PrintWriter out) {

		out.println("<style>");
		out.println("/* General styling for the HTML page */\r\n" + 
				"body {\r\n" + 
				"    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\r\n" + 
				"    background-color: #000;\r\n" + 
				"    color: #fff;\r\n" + 
				"    margin: 0;\r\n" + 
				"    padding: 0;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				".container {\r\n" + 
				"    max-width: 400px;\r\n" + 
				"    margin: 50px auto;\r\n" + 
				"    background-color: #222;\r\n" + 
				"    padding: 20px;\r\n" + 
				"    border-radius: 8px;\r\n" + 
				"    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"h2 {\r\n" + 
				"    text-align: center;\r\n" + 
				"    color: #00ffea;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"form {\r\n" + 
				"    margin-top: 20px;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"label {\r\n" + 
				"    display: block;\r\n" + 
				"    margin-bottom: 5px;\r\n" + 
				"    color: #aaa;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"input[type=\"text\"],\r\n" + 
				"input[type=\"password\"] {\r\n" + 
				"    width: 100%;\r\n" + 
				"    padding: 10px;\r\n" + 
				"    margin-bottom: 10px;\r\n" + 
				"    border: 1px solid #333;\r\n" + 
				"    border-radius: 5px;\r\n" + 
				"    background-color: #333;\r\n" + 
				"    color: #fff;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"input[type=\"submit\"] {\r\n" + 
				"    width: 100%;\r\n" + 
				"    padding: 10px;\r\n" + 
				"    border: none;\r\n" + 
				"    border-radius: 5px;\r\n" + 
				"    background-color: #00ffea;\r\n" + 
				"    color: #000;\r\n" + 
				"    cursor: pointer;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"input[type=\"submit\"]:hover {\r\n" + 
				"    background-color: #00d4bd;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"a {\r\n" + 
				"    display: block;\r\n" + 
				"    text-align: center;\r\n" + 
				"    color: #00ffea;\r\n" + 
				"    text-decoration: none;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"a:hover {\r\n" + 
				"    text-decoration: underline;\r\n" + 
				"}\r\n" + 
				"");
		out.println("</style>");
	}
	public void cssHomePage(PrintWriter out) {
		
		out.println("<script>");
		out.println("	    function hideScore_show() {\r\n" + 
				"	        var infoBox = document.getElementById(\"score_show\");\r\n" + 
				"	        infoBox.style.display = \"none\"; // Hide the info box\r\n" + 
				"	    }\r\n" + 
				"\r\n" + 
				"	    setTimeout(hideScore_show, 4000); ");
		out.println("</script>");
	}


}
