package ctu.cit.anchaunhut;

public class Options {
	private String option_id;
	private String question_id;
	private String option_text;
	private String option_image;
	private boolean is_correct;

	public String getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}

	public String getOption_text() {
		return option_text;
	}

	public void setOption_text(String option_text) {
		this.option_text = option_text;
	}

	public String getOption_image() {
		return option_image;
	}

	public void setOption_image(String option_image) {
		this.option_image = option_image;
	}

	public boolean isIs_correct() {
		return is_correct;
	}

	public void setIs_correct(boolean is_correct) {
		this.is_correct = is_correct;
	}

	@Override
	public String toString() {
		return "{\"question_id\":" + question_id + ", \"option_text\":" + option_text + ", \"option_id\":" + option_id
				+ ", \"is_correct\":\"" + is_correct + ", \"option_image\":" + option_image + "\"}";
	}

	public String getOption_id() {
		return option_id;
	}

	public void setOption_id(String option_id) {
		this.option_id = option_id;
	}

}
