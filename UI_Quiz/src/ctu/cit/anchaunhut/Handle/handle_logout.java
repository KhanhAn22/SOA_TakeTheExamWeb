package ctu.cit.anchaunhut.Handle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/handle_logout")
public class handle_logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public handle_logout() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
	    if (session != null) {
	        session.invalidate(); 
	    }
	    response.sendRedirect("/UI_Quiz/loginPage"); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
