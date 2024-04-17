package ctu.cit.anchaunhut;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("questions")
public class QuestionsController {
	
	private static QuestionsService questionsService = new QuestionsService();
	
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addNewQuestions(Questions question) throws ClassNotFoundException {  
        
        return questionsService.addQuestion(question);
	}
	
	// Read 1 Question by question_id
	@GET
	@Path("/read")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readQuestions(@QueryParam("question_id") String question_id) throws ClassNotFoundException, SQLException {  
        
        return questionsService.readQuestion(question_id);

	}
	
	//	Return List Questions of Quiz_id
	@GET
	@Path("/readAll")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readAllQuestionsWithQuizId(@QueryParam("quiz_id") String quiz_id) throws ClassNotFoundException, SQLException {  
        
        return questionsService.readAllQuestionsWithQuizId(quiz_id);

	}
	
	
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteQuestion(@QueryParam("question_id") String question_id) throws ClassNotFoundException, SQLException {  
        
        return questionsService.deleteQuestion(question_id);
	}
	
	@GET
	@Path("/deleteWithQuizId")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String delet_All_Question_With_QuizID(@QueryParam("quiz_id") String quiz_id) throws ClassNotFoundException, SQLException {  
        
        return questionsService.delete_All_Question_With_QuizID(quiz_id);
	}
	
	@GET
	@Path("/checkCorrect")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String checkCorrectQuestion(@QueryParam("question_id") String question_id, @QueryParam("Uanswer") String Uanswer) throws ClassNotFoundException, SQLException {  
        
        return questionsService.checkCorrectQuestion(question_id,Uanswer);
	}
	
	
}
