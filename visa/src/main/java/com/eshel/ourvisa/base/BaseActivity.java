package com.eshel.ourvisa.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.base.wrap.WrapActivity;
import com.eshel.ourvisa.util.UIUtil;

@SuppressLint("Registered")
public class BaseActivity<TitleHolder extends BaseTitleHolder> extends WrapActivity implements StateViewProvider{
    protected BaseActivityHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        mHelper = new BaseActivityHelper<TitleHolder>(this);
        mHelper.onCreate(savedInstanceState, new StateManager(this),new TitleManager<>(initTitleHolder(),this,initTitleMode()));
    }

    public void switchEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        UIUtil.showSoftInput(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHelper.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHelper.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.onStop();
    }

    /**
     * 是否需要 Activity 进出动画
     */
    public boolean isNeedAnim(){
        return mHelper.needAnim;
    }

    /**
     * 设置 Activity 进出动画
     * isNeed is true 代表需要动画, 默认 true,
     * isNeed is false 代表不需要动画
     * 需要在 activity 中 super.onCreate()之后调用
     */
    public void setNeedAnim(boolean isNeed){
        mHelper.needAnim = isNeed;
    }

    /**
     * @see State
     * @param state {@link State#STATE_LOADING}, {@link State#STATE_EMPTY}, {@link State#STATE_SUCCESS}, {@link State#STATE_FAILED}
     */
    public void setState(int state){
        mHelper.getStateManager().setState(state);
    }

    public BaseActivityHelper getHelper() {
        return mHelper;
    }

    public StateManager getStateManager(){
        return mHelper.getStateManager();
    }

    public TitleManager<TitleHolder> getTitleManager(){
        return mHelper.getTitleManager();
    }

    /**
     * @return null 代表无标题
     */
    public TitleHolder initTitleHolder(){
        return null;
    }

    protected int initTitleMode() {
        return TitleManager.MODE_VERTICAL;
    }

    @Override
    public void setContentView(View view) {
        mHelper.setContentView(view);
//        super.setContentView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        mHelper.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mHelper.setContentView(view,params);
    }

    public View loading(){
        return View.inflate(this, R.layout.home_loading,null);
//        return UIUtil.getDefaultFragmentLayout(this);
    }

    /**
     * 使用 loadSuccess 不能 return null , return null 会导致界面没有内容
     */
    public View loadSuccess(){
        return null;
    }

    private View getLoadFailedView(){
        View view = loadFailed();
        View viewc = view.findViewById(R.id.try_again);
        if(viewc == null)
            viewc = view;
        viewc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLoadFailed();
            }
        });
        return view;
    }

    public View loadFailed(){
        return View.inflate(this, R.layout.home_load_failed, null);
    }

    public void clickLoadFailed(){
        UIUtil.debugShortToast("click load failed view");
    }

    public void clickLoadEmpty(){
        UIUtil.debugShortToast("click load empty view");
    }

    private View getLoadEmptyView(){
        View view = loadEmpty();
        if(view != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickLoadEmpty();
                }
            });
        return view;
    }

    public View loadEmpty(){
        return UIUtil.getDefaultFragmentLayout(this);
    }

    public View getContentView(){
        return mHelper.getContentView();
    }

    public int getContentViewResId(){
        return mHelper.getContentViewResId();
    }

    @Override
    protected void onDestroy() {
        mHelper.onDestroy();
        super.onDestroy();
//        BaseApplication.getRefWatcher().watch(this); 检测内存泄露框架
    }

    public<T extends Activity> void startActivity(Class<T> clazz) {
        mHelper.startActivity(clazz);
    }

    public Intent createIntent() {
        return mHelper.createIntent();
    }

    public<T extends BaseActivity> Intent createIntent(Class<T> actClass1) {
        return mHelper.createIntent(actClass1);
    }

    public void setStateBarHeight(View stateBarEmpty){
        UIUtil.setViewHeight(stateBarEmpty,UIUtil.getStateBarHeight());
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BaseActivity.this.finish();
        }
    };

    public void setBackView(View view){
        if(view != null)
            view.setOnClickListener(backListener);
    }
}
