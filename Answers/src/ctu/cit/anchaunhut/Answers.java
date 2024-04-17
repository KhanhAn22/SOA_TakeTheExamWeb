package ctu.cit.anchaunhut;

public class Answers {
	
	private String answer_id;
	private String session_id;
    private String question_id;
    private String user_answer;
    private Boolean is_correct;
    
	public String getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(String answer_id) {
		this.answer_id = answer_id;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	public String getUser_answer() {
		return user_answer;
	}
	public void setUser_answer(String user_answer) {
		this.user_answer = user_answer;
	}
	public Boolean getIs_correct() {
		return is_correct;
	}
	public void setIs_correct(Boolean is_correct) {
		this.is_correct = is_correct;
	}
	@Override
	public String toString() {
		return "{\"answer_id\":\"" + answer_id + "\", \"session_id\":\"" + session_id + "\", \"question_id\":\"" + question_id
				+ "\", \"user_answer\":\"" + user_answer + "\", \"is_correct\":" + is_correct + "}";
	}
   
}
