package com.example.netty.msg;

public class PingMsg extends BaseMsg {

    private String pingPackage;

    public PingMsg() {
        super();
        setType(MsgType.PING);
    }

    public String getPingPackage() {
        return pingPackage;
    }

    public void setPingPackage(String pingPackage) {
        this.pingPackage = pingPackage;
    }
}
