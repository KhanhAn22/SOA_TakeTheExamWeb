package ctu.cit.anchaunhut.Handle;

import java.io.IOException;
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

@WebServlet("/handle_signUp")
public class handle_signUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String username = "";
	String password = "";
	String email = "";

	public handle_signUp() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		getInformation(parameterMap);
		
		// Insert new user
		if(insertNewUser()) {
			response.sendRedirect("/UI_Quiz/loginPage?createUser=success");
		} else {
			response.sendRedirect("/UI_Quiz/signUpUser?createUser=\"fail\"");
		}
		
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void getInformation(Map<String, String[]> parameterMap) {

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {

			String parameterName = entry.getKey();
			String[] paramValues = entry.getValue();

			for (String paramValue : paramValues) {
				if (parameterName.compareTo("username") == 0) {
					username = paramValue;
				} else if (parameterName.compareTo("password") == 0) {
					password = paramValue;
				} else if (parameterName.compareTo("email") == 0) {
					email = paramValue;
				}

			}
		}
	}
	
	private Boolean insertNewUser() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Users/api/user/new").build();

		WebTarget target = client.target(uri);

		// Create a JSON object representing your request data
		JsonObject requestData = Json.createObjectBuilder()
				.add("username", username)
				.add("password", password)
				.add("email", email)
				.build();

		// Send a POST request with the JSON data
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(requestData));

		// Read the response body
		String jsonResponse = response.readEntity(String.class);
		if(jsonResponse.compareTo("A new \\ USER \\ was added successfully.") == 0) {
			return true;
		} else return false;
	}

}
