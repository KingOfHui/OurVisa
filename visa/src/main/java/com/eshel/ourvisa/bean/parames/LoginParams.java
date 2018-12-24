package com.eshel.ourvisa.bean.parames;

import com.eshel.ourvisa.bean.net.BaseBean;
import com.google.gson.annotations.SerializedName;

public class LoginParams extends BaseBean {
    /**
     * loginId : zhihuibu
     * password : 123456
     * termId : IMEI编号
     * termOs : android
     */
    public String loginId;
    public String password;
    public String termId;
    public String termOs;
}
