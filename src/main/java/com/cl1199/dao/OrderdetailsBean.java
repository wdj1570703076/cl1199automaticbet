package com.cl1199.dao;

public class OrderdetailsBean {
    private String Status;

    private String Msg;

    private Data data;

    private User User;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public com.cl1199.dao.User getUser() {
        return User;
    }

    public void setUser(com.cl1199.dao.User user) {
        User = user;
    }
}
