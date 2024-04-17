package ctu.cit.anchaunhut;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("options")
public class OptionsController {
	
	private static OptionsService optionsService = new OptionsService();
	
	// Add new 1 Option
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addNewOption(Options options) throws ClassNotFoundException {  
        return optionsService.addOption(options);
	}
	// Read 1 Option by option_id
	@GET
	@Path("/read")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readOption(@QueryParam("id") String options_id) throws ClassNotFoundException, SQLException {  
        
        return optionsService.readOption(options_id);

	}
	
	//	Return List Options by Question_id is parameter
	@GET
	@Path("/readAll")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public String readAllOptionWithQuestionId(@QueryParam("question_id") String options_id) throws ClassNotFoundException, SQLException {  
        
        return optionsService.readAllOptionByQuestionId(options_id);

	}
	
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteOption(Options options) throws ClassNotFoundException, SQLException {  
        
        return optionsService.deleteOption(options.getOption_id());
	}
}
