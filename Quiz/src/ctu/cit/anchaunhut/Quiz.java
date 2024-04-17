package ctu.cit.anchaunhut;

import java.util.Date;

public class Quiz {
	private String quiz_id;
	private String quiz_title;
    private String quiz_description;
    private String creator_id;
    private Date created_at;
    
	public String getQuiz_id() {
		return quiz_id;
	}
	public void setQuiz_id(String quiz_id) {
		this.quiz_id = quiz_id;
	}
	public String getQuiz_title() {
		return quiz_title;
	}
	public void setQuiz_title(String quiz_title) {
		this.quiz_title = quiz_title;
	}
	public String getQuiz_description() {
		return quiz_description;
	}
	public void setQuiz_description(String quiz_description) {
		this.quiz_description = quiz_description;
	}
	public String getCreator_id() {
		return creator_id;
	}
	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Quiz() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Quiz(String quiz_id, String quiz_title, String quiz_description, String creator_id, Date created_at) {
		super();
		this.quiz_id = quiz_id;
		this.quiz_title = quiz_title;
		this.quiz_description = quiz_description;
		this.creator_id = creator_id;
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "{\"quiz_id\":\"" + quiz_id + "\", \"quiz_title\":\"" + quiz_title + "\", \"quiz_description\":\"" + quiz_description
				+ "\", \"creator_id\":\"" + creator_id + "\", \"created_at\":\"" + created_at + "\"}";
	}
	
//	This function combine toString() + Call API of Questions to return list of "Question with Quiz_id is parameter"
	public String toString_with_QuizID(String quiz_id) {
		return "{\"quiz_id\":\"" + quiz_id + "\", \"quiz_title\":\"" + quiz_title + "\", \"quiz_description\":\"" + quiz_description
				+ "\", \"creator_id\":\"" + creator_id + "\", \"created_at\":\"" + created_at 
				+ "\",\"question\":" + QuizService.read_All_Question_With_Quiz_id(quiz_id)
				+ "}";
	}
    
    
    

}
