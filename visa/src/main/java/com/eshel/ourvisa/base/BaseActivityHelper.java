package com.eshel.ourvisa.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.util.UIUtil;


public class BaseActivityHelper<TitleHolder extends BaseTitleHolder> implements IBaseActivity{
    private ActivityCache mCache;
    private Activity mActivity;

    boolean needAnim = true;//是否需要activity进出动画

    public boolean need = false;
    private StateManager stateManager;
    private TitleManager<TitleHolder> titleManager;
    public BaseActivityHelper(Activity activity) {
        mActivity = activity;
    }

    public void onCreate(Bundle savedInstanceState, StateManager stateManager, TitleManager titleManager){
        mCache = new ActivityCache();
//        if(need)
//            UIUtil.setStateColorNone(mActivity);
        isStatusNone(need);
        this.stateManager = stateManager;
        this.titleManager = titleManager;
        if(stateManager != null) {
            titleManager.bindToStateManager(stateManager);
        }
    }

    public void onResume(){
        if(!needAnim) {
            mActivity.overridePendingTransition(0, 0);
        }
    }

    public void onPause(){
        if(!needAnim)
            mActivity.overridePendingTransition(0, 0);
    }
    /**
     * 和加载 loadSuccess 不兼容, 使用 setContentView 可能会导致 loadSuccess 失效, 使用loadSuccess 可能会导致 setContentView 失效
     * 二者则一
     */
    public void setContentView(View view) {
        setContentView(view,null);
    }

    public void isStatusNone(boolean b) {
        if (b) {
            UIUtil.setStateColorNone(mActivity);
        } else {
            return;
        }
    }
    /**
     * @see BaseActivityHelper#setContentView(View)
     */
    public void setContentView(int layoutResID) {
        mCache.layoutResID = layoutResID;
        if(stateManager != null){
            stateManager.onlySetState(State.STATE_LOADING);
            stateManager.reSetState(State.STATE_SUCCESS,null);
            stateManager.reSetState(State.STATE_SUCCESS, layoutResID);
            stateManager.setState(State.STATE_SUCCESS);
        }else {
            titleManager.setContentView(layoutResID);
        }
        if(titleManager.getTitleHolder() == null){
            View stateBarEmpty = titleManager.getContentView().findViewById(R.id.state_bar_empty);
            if(stateBarEmpty != null){
                UIUtil.setViewHeight(stateBarEmpty,UIUtil.getStateBarHeight());
            }
        }
        // 将所有布局添加至 Activity 中
        init();
    }

    public void init(){
        if(mActivity instanceof AppCompatActivity)
            ((AppCompatActivity) mActivity).getDelegate().setContentView(titleManager.init());
        else {
            mActivity.getWindow().setContentView(titleManager.init());
        }
    }

    /**
     * @see BaseActivityHelper#setContentView(View)
     */
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mCache.contentView = view;
        mCache.mParams = params;
        if(stateManager != null){
            stateManager.onlySetState(State.STATE_LOADING);
            stateManager.reSetState(State.STATE_SUCCESS,null);
            stateManager.reSetState(State.STATE_SUCCESS, view, params);
            stateManager.setState(State.STATE_SUCCESS);
        }else {
            titleManager.setContentView(view);
        }
        if(titleManager.getTitleHolder() == null){
            View stateBarEmpty = mActivity.findViewById(R.id.state_bar_empty);
            if(stateBarEmpty != null){
                UIUtil.setViewHeight(stateBarEmpty,UIUtil.getStateBarHeight());
            }
        }
        init();
    }

    public View getContentView(){
        if(mCache == null)
            return null;
        return mCache.contentView;
    }

    public int getContentViewResId(){
        if(mCache == null)
            return 0;
        return mCache.layoutResID;
    }

    public void onDestroy() {
        mActivity = null;
    }

    public void onStart(){
    }

    public void onStop(){
    }

    @Override
    public<T extends BaseActivity> void startActivity(Class<T> clazz) {
        mActivity.startActivity(new Intent(mActivity, clazz));
    }

    @Override
    public Intent createIntent() {
        return new Intent();
    }

    @Override
    public <T extends BaseActivity>Intent createIntent(Class<T> actClass1) {
        return new Intent(mActivity, actClass1);
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public TitleManager<TitleHolder> getTitleManager() {
        return titleManager;
    }

    class ActivityCache{
        View contentView;
        int layoutResID;
        ViewGroup.LayoutParams mParams;
    }
}