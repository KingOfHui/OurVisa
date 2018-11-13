package com.eshel.ourvisa.base;

import android.view.View;
import android.view.ViewGroup;

import com.eshel.ourvisa.util.Log;

import java.util.ArrayList;

/**
 * createBy Eshel
 * createTime: 2018/7/21 20:28
 * desc: Activity Fragment 等基类 loading loadingSuccess failed empty 等状态管理类
 */
public class StateManager {
    private StateViewProvider mViewProvider;
    private int mState = -2;
    ViewGroup container;
    private ArrayList<State> mStates;

    public StateManager(StateViewProvider viewProvider){
        mStates = new ArrayList<>();
        this.mViewProvider = viewProvider;
    }

    public StateManager(View loadingView) {
        mStates = new ArrayList<>();
        setLoadingStateView(loadingView);
    }

    public void initState(ViewGroup container){
        if(container == null)
            throw new NullPointerException("container can not null");
        this.container = container;
        int state = mState;
        mState = -2;
        if(state == -2)
            state = State.STATE_LOADING;
        setState(state);
    }

    public int getState(){
        return mState;
    }

    /**
     * 改变加载状态同时改变加载状态对应视图
     * @param state
     */
    public void setState(int state){
        if(mState == state)
            return;
        if(container == null){
            mState = state;
            return;
        }
        container.removeAllViews();
        State stateS = null;
        try {
            stateS = getState(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(stateS == null)
            return;
        mState = state;
        if(stateS.stateView != null) {
            ViewGroup parent = (ViewGroup) stateS.stateView.getParent();
            if(stateS.stateView.getParent() != null){
                parent.removeView(stateS.stateView);
//                Log.log("error...");
            }
            if (stateS.stateViewParames != null)
                container.addView(stateS.stateView, stateS.stateViewParames);
            else
                container.addView(stateS.stateView);
        }else if(stateS.layoutId != 0){
            View.inflate(container.getContext(),stateS.layoutId,container);
            stateS.stateView = container.getChildAt(0);
        }
    }

    private State getState(int state) throws Exception {
        State stateObj = State.findStateByCode(mStates, state);
        if(stateObj != null)
            return stateObj;
        try {
            switch (state){
                case State.STATE_LOADING:
                    return setLoadingStateView(mViewProvider.loading());
                case State.STATE_EMPTY:
                    return setEmptyStateView(mViewProvider.loadEmpty());
                case State.STATE_FAILED:
                    return setFailedStateView(mViewProvider.loadFailed());
                case State.STATE_SUCCESS:
                    return setSuccessStateView(mViewProvider.loadSuccess());
            }
        }catch (Exception e){
            Log.w("StateManager", "error !!! mViewProvider is null!!");
        }
        if(state >= -2 && state <= 2)
            throw new Exception("state inexistence, frist use [setSuccessStateView(), setFailedStateView(), " +
                    "setEmptyStateView(), setLoadingStateView] method init state view");
        else
            throw new Exception("state inexistence, first use addState() method init state");
    }

    public final State setSuccessStateView(View view){
        return reSetState(State.STATE_SUCCESS,view);
    }

    public boolean hasState(int state){
        return State.findStateByCode(mStates,state) != null;
    }
    public final State setFailedStateView(View view){
        return reSetState(State.STATE_FAILED,view);
    }
    public final State setEmptyStateView(View view){
        return reSetState(State.STATE_EMPTY,view);
    }
    public final State setLoadingStateView(View view){
        return reSetState(State.STATE_LOADING,view);
    }

    /**
     * 重新设置 state
     */
    public final State reSetState(int stateCode, View view){
        return reSetState(stateCode,view,null);
    }

    /**
     * @see StateManager#reSetState(int, View)
     */
    public final void reSetState(int stateCode, int layoutResId){
        State state = State.findStateByCode(mStates, stateCode);
        if(state == null) {
            state = new State(stateCode, null);
        }
        state.layoutId = layoutResId;
        mStates.add(state);
    }

    /**
     * @see StateManager#reSetState(int, View)
     */
    public final State reSetState(int stateCode, View view, ViewGroup.LayoutParams params){
        State state = State.findStateByCode(mStates, stateCode);
        if(state == null) {
            state = new State(stateCode, view);
            state.stateViewParames = params;
            mStates.add(state);
        }else {
            state.stateView = view;
            state.stateViewParames = params;
        }
        return state;
    }

    /**
     * 为界面新增状态
     */
    public final void addState(State state){
        if(state.stateCode >= -2 && state.stateCode <= 2){
            new Exception("stateCode can not use [-2, -1, 0, 1, 2], plase use other number").printStackTrace();
            return;
        }
        if(mStates != null && mStates.size() > 14){
            new Exception("state max size is ten!!!").printStackTrace();
            return;
        }
        if(state != null){
            if(mStates == null)
                mStates = new ArrayList<>();
            mStates.add(state);
        }
    }

    public void onlySetState(int state){
        this.mState = state;
    }
}
