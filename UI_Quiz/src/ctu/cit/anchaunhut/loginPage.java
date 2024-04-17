package ctu.cit.anchaunhut;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ctu.cit.anchaunhut.Controller.UIServiceController;

@WebServlet("/loginPage")
public class loginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UIServiceController uiServiceController = new UIServiceController();

	public loginPage() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Login Page</title></head><body>");

//		uiServiceController.loginPage(out);

		String createUser = request.getParameter("createUser");
		String loginFail = request.getParameter("loginFail");

		out.println("<div>");
		out.println("<form action=\"handle_Login\" method=\"post\">");
		out.println("    <label for=\"username\">Username:</label><br>");
		out.println("    <input type=\"text\" id=\"username\" name=\"userName\" required><br><br>");
		out.println("    <label for=\"password\">Password:</label><br>");
		out.println("    <input type=\"password\" id=\"password\" name=\"passWord\" required><br><br>");
		out.println("    <input type=\"submit\" value=\"Login\">");
		out.println("	 <a href=\"/UI_Quiz/signUpUser\">Signup</a>");
		out.println("</form>");
		out.println("</div>");

		if (createUser != null) {
			if (createUser.compareTo("success") == 0) {
				out.println("Create User SUCCESS");
			}
		}
		if (loginFail != null) {
			if (loginFail.compareTo("oke") == 0) {
				out.println("<p> Wrong userName or passWord </p>");
			}
		}

		uiServiceController.cssLoginPage(out);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
