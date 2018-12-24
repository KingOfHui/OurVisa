package com.eshel.ourvisa.bean.parames;

import com.eshel.ourvisa.bean.net.BaseBean;

public class Params<Data extends BaseBean> extends BaseBean{

    private String command;
    private String sign;
    private Data data;
    private String token;

    public Params(String command, Data data) {
        this.command = command;
        this.data = data;
    }

    public Params(String command, String token) {
        this.command = command;
        this.token = token;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
