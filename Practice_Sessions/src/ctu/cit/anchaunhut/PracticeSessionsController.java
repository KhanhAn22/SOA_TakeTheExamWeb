package ctu.cit.anchaunhut;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("practiceSessions")
public class PracticeSessionsController {

	private static PracticeSessionsService practiceSessionsService = new PracticeSessionsService();

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addNewPracticeSessions(PracticeSessions practiceSessions) throws ClassNotFoundException {

		return practiceSessionsService.addNewPracticeSessions(practiceSessions);
	}

	// Return 1 session by session_id
	@GET
	@Path("/read")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readPracticeSessions(@QueryParam("session_id") String session_id)
			throws ClassNotFoundException, SQLException {

		return practiceSessionsService.readPracticeSessions(session_id);

	}
	
	@GET
	@Path("/readScore")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readPracticeSessionsScore(@QueryParam("session_id") String session_id)
			throws ClassNotFoundException, SQLException {
		return practiceSessionsService.readPracticeSessionsScore(session_id);
	}

	//	Return List of Sessions + Answer by Quiz_id + User_id is parameter
	@GET
	@Path("/readAll")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readAllSessionByUserIdvsQuizId(@QueryParam("quiz_id") String quiz_id,
			@QueryParam("user_id") String user_id) throws ClassNotFoundException, SQLException {
		return practiceSessionsService.readAllSessionByUserIdvsQuizId(quiz_id, user_id);

	}


}
