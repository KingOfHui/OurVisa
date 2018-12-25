package com.eshel.ourvisa.mvp.modles;

import com.eshel.ourvisa.bean.net.User;

public interface IUserModle {

    User getUser();
    void saveUser(User user);
}
