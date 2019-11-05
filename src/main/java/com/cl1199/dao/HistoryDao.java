package com.cl1199.dao;

import java.util.List;

public class HistoryDao {
	private int Status;
	
	private String Msg;
	
	private List<Data> Data;
	
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
	
	public List<Data> getData() {
		return Data;
	}

	public void setData(List<Data> data) {
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
		return "HistoryDao [Status=" + Status + ", Msg=" + Msg + ", Data=" + Data + ", User=" + User + "]";
	}

}
