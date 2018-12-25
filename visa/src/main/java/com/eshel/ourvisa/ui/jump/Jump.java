package com.eshel.ourvisa.ui.jump;

import android.content.Context;

import com.eshel.ourvisa.ui.jump.anno.Intent;
import com.eshel.ourvisa.ui.jump.anno.Params;
import com.eshel.ourvisa.ui.user.login.LoginActivity;

public interface Jump {
    @Intent(target = LoginActivity.class)
    void jumpLogin(Context context, @Params(key = "NEED_BACK") boolean needBackBtn);
}
