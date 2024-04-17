package ctu.cit.anchaunhut.Admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ctu.cit.anchaunhut.Controller.AdminController;

@WebServlet("/DashBoard_Admin")
public class DashBoard_Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	AdminController adminController = new AdminController();
	PrintWriter out = null;


	public DashBoard_Admin() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		
		out = response.getWriter();

		PrintWriter out = response.getWriter();

		out.println("<h1> Admin DashBoard </h1>");
		out.println("<a href=\"/UI_Quiz/handle_logout\">Logout</a>");

		out.println("<a class=\"button\" onclick=\"addNewQuiz()\">Add New Quiz</a><br>");
		
		
		// When create New Quiz, and have user take the exam, can't delete question 
		// Form Add new Quiz
		formAddNewQuiz();

		// Read all Quiz - each quiz have it own box.
		// SERVICE - Include Quiz 
		adminController.readAllQuiz(out);

//		Import CSS + JS

		adminController.cssDashboardAdmin(out);
		adminController.scriptDashboardAdmin(out);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void formAddNewQuiz() {
		out.println("<br><div id=\"quizFormDiv1\">");
		out.println(
				"<form id=\"quizFormDiv\" style=\"display:none; \" action=\"/UI_Quiz/handle_addNewQuiz\" method=\"POST\">");
		out.println("    <input type=\"hidden\" id=\"creator_id\" name=\"creator_id\" value=\"1\">");
		out.println("");
		out.println("    <label for=\"quiz_title\">Quiz Title:</label><br>");
		out.println("    <input type=\"text\" id=\"quiz_title\" name=\"quiz_title\" required><br><br>");
		out.println("");
		out.println("    <label for=\"quiz_description\">Quiz Description:</label><br>");
		out.println("    <textarea id=\"quiz_description\" name=\"quiz_description\" required></textarea><br><br>");
		out.println("");
		out.println("    <input type=\"submit\" value=\"Submit\">");
		out.println("</form>");
		out.println("</div>");
	}


}
