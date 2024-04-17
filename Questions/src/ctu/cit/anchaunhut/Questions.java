package ctu.cit.anchaunhut;

public class Questions {
	private String question_id;
	private String quiz_id;
    private String question_text;
    
    
	public Questions(String question_id, String quiz_id, String question_text) {
		super();
		this.question_id = question_id;
		this.quiz_id = quiz_id;
		this.question_text = question_text;
	}
	
	
	public Questions() {
		super();
	}


	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	public String getQuiz_id() {
		return quiz_id;
	}
	public void setQuiz_id(String quiz_id) {
		this.quiz_id = quiz_id;
	}
	public String getQuestion_text() {
		return question_text;
	}
	public void setQuestion_text(String question_text) {
		this.question_text = question_text;
	}
	@Override
	public String toString() {
		return "{\"question_id\":" + question_id + ", \"question_text\":" + question_text			
				+ "}";
	}
	
//	This function combine toString of Questions + call API of Options to return List of "Options with parameter is Question_id"
	public String readWithOptions(String question_id) {
		return "{\"question_id\":\"" + question_id + "\", \"question_text\":\"" + question_text + "\", \"quiz_id\":\"" + quiz_id 
				+ "\", \"options\":" + QuestionsService.read_All_Option_With_Question_Id(question_id)
				+ "}";
	}

    
    
	
	
	
    
    

}
