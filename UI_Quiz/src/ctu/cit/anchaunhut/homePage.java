package ctu.cit.anchaunhut;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ctu.cit.anchaunhut.Controller.UIServiceController;

@WebServlet("/homePage")
public class homePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UIServiceController UiService = new UIServiceController();

	UIServiceController uiServiceController = new UIServiceController();
    public homePage() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		
		// Session of Quiz recently try test
		String session_id = (String) session.getAttribute("session_id");
		String number_correct = session.getAttribute("number_correct") + "";
		
		

		if(session_id  != null && number_correct != null) {
			out.println("<div id=\"score_show\">");
			out.println("<h1> Your SCORE is : " + number_correct + "</h1>");
			out.println("<h1> Your session_id is : " + session_id + "</h1>");
			out.println("</div>");
		}
		if(session.getAttribute("userName") == null) {
			response.sendRedirect("/UI_Quiz/loginPage");
		}

		// Get infor from session
		String user_id = (String) session.getAttribute("user_id");
		String user_name = (String) session.getAttribute("user_name");
		
		// This print use to test===================================
		out.println("<h1> USER ID: " + user_id + "</h1>");
		out.println("<h1> user_name: " + user_name + "</h1>");
		// This print use to test===================================
		
		out.println("<a href=\"/UI_Quiz/handle_logout\">Logout</a>");


		out.println("<html><head><title>Home Page</title></head><body>");

		out.println("<h1>This is home Page</h1>");
		
		out.println("<h1>Choose one Quiz and make exam </h1>");
		
		// SERVICE - Get quiz by user_id  - ( Quiz + Session of that Quiz )
		uiServiceController.getAllQuiz(out, user_id);
		
		// Css
		uiServiceController.cssHomePage(out);
		UiService.css(out);
		UiService.script(out);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
