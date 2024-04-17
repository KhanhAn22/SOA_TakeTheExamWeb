package ctu.cit.anchaunhut;

import java.time.LocalDateTime;

public class PracticeSessions {
	
	private String session_id;
	private String user_id;
    private String quiz_id;
    private LocalDateTime  end_date;
    private LocalDateTime start_date;

	public PracticeSessions() {
		super();
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}




	public String getQuiz_id() {
		return quiz_id;
	}

	public void setQuiz_id(String quiz_id) {
		this.quiz_id = quiz_id;
	}


	public LocalDateTime getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDateTime end_date) {
		this.end_date = end_date;
	}

	public LocalDateTime getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDateTime start_date) {
		this.start_date = start_date;
	}

	@Override
	public String toString() {
		return "{\"session_id\":\"" + session_id + "\", \"user_id\":\"" + user_id + "\", \"quiz_id\":\"" + quiz_id
				+ "\", \"end_date\":\"" + end_date + "\", \"start_date\":\"" + start_date + "\"}";
	}
	
	// Return information of PracticeSession + Call API readAll of Answer to return list of answer
	public String readWithAnswer(String session_id) {
		return "{\"session_id\":\"" + session_id + "\", \"user_id\":\"" + user_id + "\", \"quiz_id\":\"" + quiz_id
				+ "\", \"end_date\":\"" + end_date + "\", \"start_date\":\"" + start_date + "\","
				+ "\"answers\":" + PracticeSessionsService.read_All_Answers_With_SessionId(session_id)
						+ "}";
	}
	
	

	
}
