package com.eshel.ourvisa.ui.jump;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eshel.ourvisa.base.BaseActivity;
import com.eshel.ourvisa.ui.jump.anno.IntentParser;
import com.eshel.ourvisa.ui.jump.anno.Params;

public class DemoAct extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JumpUtil.parseIntent(this, getIntent());
    }

    @IntentParser(intentType = IntentType.Intent)
    public void parseIntent(@Params(key = "NEED_BACK") boolean needBackBtn){

    }
}
