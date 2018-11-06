package com.eshel.ourvisa.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public interface IBaseActivity {
    void onCreate(Bundle savedInstanceState, StateManager stateManager, TitleManager titleManager);
    void setContentView(View view);
    void setContentView(int layoutResID);
    void setContentView(View view, ViewGroup.LayoutParams params);
    View getContentView();
    int getContentViewResId();
    void onDestroy();
    <T extends BaseActivity>void startActivity(Class<T> actClass1);
    Intent createIntent();
    <T extends BaseActivity>Intent createIntent(Class<T> actClass1);
}
