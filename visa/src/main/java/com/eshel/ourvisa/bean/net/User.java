package com.eshel.ourvisa.bean.net;

import com.google.gson.annotations.SerializedName;

public class User extends BaseBean{
    public static final String USER_ID = "userId";
    /**
     * res : http://localhost:7003/enterprise-platform/resource
     * orgName : 所属单位，没有为空
     * loginName : 13876543210
     * sex : 1
     * mobile : 13876543210
     * photo : 2018/12/25415593869595.jpg
     * userName : 指挥部管理员
     * personSay : 2018/12/25415593869595_s.jpg
     * userId : 10308988192809
     * orgId : 100
     * token : NzU0MzQ0NjIxMjQ1NjAwMTAzMDg5ODgxOTI4MDk=
     */

    @SerializedName("res")
    public String res;
    @SerializedName("orgName")
    public String orgName;
    @SerializedName("loginName")
    public String loginName;
    @SerializedName("sex")
    public int sex;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("photo")
    public String photo;
    @SerializedName("userName")
    public String userName;
    @SerializedName("personSay")
    public String personSay;
    @SerializedName("userId")
    public long userId;
    @SerializedName("orgId")
    public int orgId;
    @SerializedName("token")
    public String token;
}
