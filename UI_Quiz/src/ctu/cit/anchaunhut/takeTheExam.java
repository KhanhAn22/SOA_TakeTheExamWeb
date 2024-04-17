package ctu.cit.anchaunhut;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ctu.cit.anchaunhut.Controller.UIServiceController;

@WebServlet("/takeTheExam")
public class takeTheExam extends HttpServlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out = null;

	UIServiceController UiService = new UIServiceController();

	String quiz_id = "";
	String user_id = "";
	public takeTheExam() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		out = response.getWriter();
		out.println("<html><head><title>Input Numbers</title></head><body>");

		Map<String, String[]> parameterMap = request.getParameterMap();

		out = response.getWriter();

		getInformation(parameterMap);
		
		if (quiz_id == null || user_id == null) {
			response.sendRedirect("/UI_Quiz/loginPage");
		}

		// SERVICE - Take the exam - ( Include Quiz_Service + Session Service )
		UiService.takeTheExam(quiz_id, user_id, out);

//		Import CSS
		UiService.css(out);
		UiService.script(out);

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
				if (parameterName.compareTo("quiz_id") == 0) {
					quiz_id = paramValue;
				} else if (parameterName.compareTo("user_id") == 0) {
					user_id = paramValue;
				}
			}
		}
	}

}
