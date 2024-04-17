package ctu.cit.anchaunhut;

import java.sql.Date;

public class Users {
	private String user_id;
	private String userName;
    private String passWord;
    private String email;
    private Date created_at;
	public String getUser_id() {
		return user_id;
	}
	
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "{\"user_id\":\"" + user_id + "\", \"userName\":\"" + userName +  "\", \"email\":\"" + email
				+"\"}";
	}
	
	
    
    
    
    
}
