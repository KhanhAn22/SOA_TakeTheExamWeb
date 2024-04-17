package ctu.cit.anchaunhut;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/signUpUser")
public class signUpUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public signUpUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		out.println("<html><head><title>Sign Up User</title></head><body>");

		out.println("<form action=\"handle_signUp\" method=\"post\">");
		out.println("    <label for=\"username\">Username:</label><br>");
		out.println("    <input type=\"text\" id=\"username\" name=\"username\" required><br><br>");
		out.println("    <label for=\"password\">Password:</label><br>");
		out.println("    <input type=\"password\" id=\"password\" name=\"password\" required><br><br>");
		out.println("    <label for=\"email\">Email:</label><br>");
		out.println("    <input type=\"email\" id=\"email\" name=\"email\" required><br><br>");
		out.println("    <input type=\"submit\" value=\"Sign Up\">");
		out.println("</form>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
