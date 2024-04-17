package ctu.cit.anchaunhut.Handle;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
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

@WebServlet("/handle_addNewQuiz")
public class handle_addNewQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String creator_id = "";
	String quiz_title = "";
	String quiz_description = "";

	PrintWriter out = null;

	public handle_addNewQuiz() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String[]> parameterMap = request.getParameterMap();

		out = response.getWriter();

		getInformation(parameterMap);

		// Add new Question:
		try {
			addNewQuiz();
		} catch (Exception e) {
			System.out.println("handle_addNewQuiz - Insert Quiz error : " + e);
			out.println("handle_addNewQuiz - Insert Quiz error : " + e);
		}

		response.sendRedirect("/UI_Quiz/DashBoard_Admin");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void getInformation(Map<String, String[]> parameterMap) {

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {

			String parameterName = entry.getKey();
			int checkquiz_title = 0;
			int checkquiz_description = 0;

			if (parameterName.compareTo("quiz_title") == 0) {
				checkquiz_title = 1;
			} else if (parameterName.compareTo("quiz_description") == 0) {
				checkquiz_description = 1;
			}

//	    	ParamValue must be String[], it can have OBJ inside
			String[] paramValues = entry.getValue();

			for (String paramValue : paramValues) {
				if (checkquiz_title == 1) {
					quiz_title = paramValue;
				} else if (checkquiz_description == 1) {
					quiz_description = paramValue;
				} else {
					creator_id = paramValue;
				}
			}

		}
	}

	private void addNewQuiz() {
		if (quiz_title != "" && quiz_description != "" && creator_id != "") {
			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);

			URI uri = UriBuilder.fromUri("http://localhost:8080/Quiz/api/quiz/new").build();

			WebTarget target = client.target(uri);

			// Create a JSON object representing your request data
			JsonObject requestData = Json.createObjectBuilder().add("quiz_title", quiz_title)
					.add("quiz_description", quiz_description).add("creator_id", creator_id).build();

			// Send a POST request with the JSON data
			Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(requestData));

			// Close the response
			response.close();
		}
	}
}
