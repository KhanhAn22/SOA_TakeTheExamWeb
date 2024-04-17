package ctu.cit.anchaunhut;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("user")
public class UsersController {

	private static UsersService usersService = new UsersService();

	// Add new User
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addNewUsers(Users question) throws ClassNotFoundException {

		return usersService.addUsers(question);
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Login(Users user)
			throws ClassNotFoundException, SQLException {

		return usersService.authentication(user);
	}



}
