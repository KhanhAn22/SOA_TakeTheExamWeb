package ctu.cit.anchaunhut.Handle;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

@WebServlet("/handle_Login")
public class handle_Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String userName = "";
	String passWord = "";
	PrintWriter out = null;

	public handle_Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String[]> parameterMap = request.getParameterMap();

		out = response.getWriter();

		// Get value of userName and passWord by parameter send by USERs
		this.getInformation(parameterMap);
		
		System.out.println("HANDLE-LOGIN: User: " + userName + " - Password: " + passWord + " try to Login !!!");
		
		// Call API Check Authentication of User
		this.checkAuthentication(request, response);
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	// Get value of userName and passWord by parameter send by USERs
	private void getInformation(Map<String, String[]> parameterMap) {
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();
			int checkUserName = 0;

			if (parameterName.compareTo("userName") == 0) {
				checkUserName = 1;
			}

//	    	ParamValue must be String[], it can have OBJ inside
			String[] paramValues = entry.getValue();

			for (String paramValue : paramValues) {
				if (checkUserName == 1) {
					userName = paramValue;
				} else {
					passWord = paramValue;
				}
			}

		}

	}

	// This function Call API of USERS to authentication
	private void checkAuthentication(HttpServletRequest request, HttpServletResponse responseMain) throws IOException {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);

		URI uri = UriBuilder.fromUri("http://localhost:8080/Users/api/user/login").build();

		WebTarget target = client.target(uri);

		JsonObject requestData = Json.createObjectBuilder().add("userName", userName).add("passWord", passWord).build();

		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(requestData));

		// Read the response body
		String jsonResponse = response.readEntity(String.class);
		response.close();
		
		// SendRedirect to Admin Page if jsonResponse is "adminLogin"
		if(jsonResponse.compareTo("adminLogin") == 0) {
			responseMain.sendRedirect("/UI_Quiz/DashBoard_Admin");
		} else if(jsonResponse.compareTo("LoginFail") != 0) {
			
			System.out.println("Login SUCCESS");
			
			HttpSession session = request.getSession();
			session.setAttribute("userName", userName);
			
			JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse));
			JsonObject userObject = jsonReader.readObject();	
			
			// Get user ID, set SESSION
			session.setAttribute("user_id", userObject.getString("user_id"));
			session.setAttribute("user_name", userObject.getString("userName"));
			session.setAttribute("email", userObject.getString("email"));
			
			responseMain.sendRedirect("/UI_Quiz/homePage");
		} else if(jsonResponse.compareTo("LoginFail") == 0) {
			responseMain.sendRedirect("/UI_Quiz/loginPage?loginFail=oke");
		}
		// == 1 mean login SUCCESS, login success return User Obj
		

	}
}
