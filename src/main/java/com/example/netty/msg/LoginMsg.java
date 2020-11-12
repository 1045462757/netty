package com.example.netty.msg;

public class LoginMsg extends BaseMsg {

    private String userName;

    private String password;

    private String loginPackage;

    public LoginMsg() {
        super();
        setType(MsgType.LOGIN);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginPackage() {
        return loginPackage;
    }

    public void setLoginPackage(String loginPackage) {
        this.loginPackage = loginPackage;
    }
}
