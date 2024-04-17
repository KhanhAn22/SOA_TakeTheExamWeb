package ctu.cit.anchaunhut.Controller;

import java.io.PrintWriter;

import ctu.cit.anchaunhut.Handle.AdminService;

public class AdminController {

	AdminService adminService = new AdminService();
	public void readAllQuiz(PrintWriter out) {
		adminService.readAllQuiz(out);
	}
	
	public void cssDashboardAdmin(PrintWriter out) {
		adminService.cssDashboardAdmin(out);
	}
	
	public void scriptDashboardAdmin(PrintWriter out) {
		adminService.scriptDashboardAdmin(out);
	}
}
