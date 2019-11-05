package com.cl1199.dao;

public class LoginDao {
	
	private int Status;
	
	private String Msg;
	
	private Data Data;
	
	private User User;

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public Data getData() {
		return Data;
	}

	public void setData(Data data) {
		Data = data;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	@Override
	public String toString() {
		return "LoginDao [Status=" + Status + ", Msg=" + Msg + ", Data=" + Data + ", User=" + User + "]";
	}
	
	
}
