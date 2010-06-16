package com.syrup.model;

public class User {

	private Long id;
	private String userName;
	private String usePassord;
	private String userEmail;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUsePassord() {
		return usePassord;
	}
	public void setUsePassord(String usePassord) {
		this.usePassord = usePassord;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
