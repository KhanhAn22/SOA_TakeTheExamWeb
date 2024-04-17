package ctu.cit.anchaunhut.Handle;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

@WebServlet("/handle_addNewQuestion")
public class handle_addNewQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out = null;
	String quiz_id = "";
	String question_text = "";
	String question_id = "";
	String is_correct = "";

	public handle_addNewQuestion() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String[]> parameterMap = request.getParameterMap();
		List<String> option_text = null;

		out = response.getWriter();

		option_text = this.getInformation(parameterMap);

		// Add new Question:
		try {
			question_id = addNewQuestion();
		} catch (Exception e) {
			System.out.println("handle_addNewQuestion - Insert QUESTION error : " + e);
			out.println("Insert QUESTION error : " + e);
		}

		int count = 1;
		try {

			for (String OptionText : option_text) {
				String numberCorrect = count + "";
				out.println("Option Text " + count++ + ": " + OptionText);
				if (is_correct.compareTo(numberCorrect) == 0) {
					out.println("CORRECT ANSWER IS : " + OptionText);
					addNewOption(OptionText, true);
				} else {
					addNewOption(OptionText, false);
				}
			}
		} catch (Exception e) {
			System.out.println("handle_addNewQuestion - Insert OPTION error : " + e);
			out.println("Insert OPTION error : " + e);
		}

	    response.sendRedirect("/UI_Quiz/DashBoard_Admin");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private String addNewQuestion() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Questions/api/questions/new").build();

		WebTarget target = client.target(uri);

		// Create a JSON object representing your request data
		JsonObject requestData = Json.createObjectBuilder().add("quiz_id", quiz_id).add("question_text", question_text)
				.build();

		// Send a POST request with the JSON data
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(requestData));

		// Throw EXCEPTION if status don't 200
		if (response.getStatus() != 200) {
			System.out.println("Insert question FAIL + response status: " + response.getStatus());
			throw new RuntimeException("Failed to add new question. Server returned status: " + response.getStatus());

		}
		// Read the response body
		String jsonResponse = response.readEntity(String.class);

		// Close the response
		response.close();

		out.println("handle_addNewQuestion.addNewQuestion  ===> Add new Question success - " + question_text);
		return jsonResponse;

	}

	private void addNewOption(String option_text, Boolean isCorrect) {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Z_Options/api/options/new").build();

		WebTarget target = client.target(uri);

		// Create a JSON object representing your request data
		JsonObject requestData = Json.createObjectBuilder().add("question_id", question_id)
				.add("option_text", option_text).add("option_image", "null").add("is_correct", isCorrect)
				.build();

		// Send a POST request with the JSON data
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(requestData));

		// Throw EXCEPTION if status don't 200
		if (response.getStatus() != 200) {
			System.out.println("Insert Option FAIL + response status: " + response.getStatus());
			throw new RuntimeException("Failed to add new Option. Server returned status: " + response.getStatus());

		}
		// Read the response body
//		String jsonResponse = response.readEntity(String.class);

		// Close the response
		response.close();

		out.println("handle_addNewQuestion.addNewOption  ===> Add new OPTIONS success - " + question_text);
	}

	private List<String> getInformation(Map<String, String[]> parameterMap) {

		List<String> option_text = new ArrayList<String>();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {

			String parameterName = entry.getKey();

			String[] paramValues = entry.getValue();

			for (String paramValue : paramValues) {
				if (parameterName.compareTo("quiz_id") == 0) {
					quiz_id = paramValue;
				} else if (parameterName.compareTo("question_text") == 0) {
					question_text = paramValue;
				} else if (parameterName.compareTo("option_text1") == 0) {
					option_text.add(paramValue);
				} else if (parameterName.compareTo("option_text2") == 0) {
					option_text.add(paramValue);
				} else if (parameterName.compareTo("option_text3") == 0) {
					option_text.add(paramValue);
				} else if (parameterName.compareTo("option_text4") == 0) {
					option_text.add(paramValue);
				} else if (parameterName.compareTo("is_correct") == 0) {
					is_correct = paramValue;
					out.println("is_correct : " + is_correct);

				}
			}
		}
		return option_text;

	}
}
