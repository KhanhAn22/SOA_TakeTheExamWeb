package ctu.cit.anchaunhut;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("answers")
public class AnswersController {
	
	private static AnswersService answersService = new AnswersService();
	
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addNewAnswers(Answers answers) throws ClassNotFoundException {  
        return answersService.addNewAnswers(answers);
	}
	
	
	// Read 1 Answer by Answer_id
	@GET
	@Path("/read")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readAnswers(@QueryParam("answer_id") String answer_id) throws ClassNotFoundException, SQLException {  
        
        return answersService.readAnswers(answer_id).toString();

	}
	
	// Return List Answer of Session_id 
	@GET
	@Path("/readAll")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readAllAnswersWithSessionId(@QueryParam("session_id") String session_id) throws ClassNotFoundException, SQLException {  
        
        return answersService.list_All_Answer_With_SessionId(session_id);

	}
	
}
