package com.eshel.ourvisa.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.base.wrap.WrapFragment;
import com.eshel.ourvisa.util.Log;
import com.eshel.ourvisa.util.UIUtil;

public abstract class BaseFragment<TitleHolder extends BaseTitleHolder> extends WrapFragment implements StateViewProvider{

    public static final int STATE_FAILED = State.STATE_FAILED;//加载失败
    public static final int STATE_LOADING = State.STATE_LOADING;//加载中
    public static final int STATE_SUCCESS = State.STATE_SUCCESS;//加载成功
    public static final int STATE_EMPTY = State.STATE_EMPTY;//空视图

    private StateManager mStateManager;
    private TitleManager<TitleHolder> mTitleManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStateManager = new StateManager(this);
        mTitleManager = new TitleManager<>(initTitleHolder(), getActivity(), initTitleMode());
    }

    protected int initTitleMode() {
        return TitleManager.MODE_VERTICAL;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ll = mTitleManager.init();
        mTitleManager.bindToStateManager(mStateManager);
        return ll;
    }

    /**
     * @return null 代表无标题
     */
    protected TitleHolder initTitleHolder(){
        return null;
    }

    public final void setState(int state){
        if(state == STATE_SUCCESS) {
            if(!mStateManager.hasState(state))
                mStateManager.setSuccessStateView(loadSuccess());
        }
        mStateManager.setState(state);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public abstract View loadSuccess();

    public View loadFailed(){
       View mFailedLayout = View.inflate(getContext(), R.layout.home_load_failed, null);
       mFailedLayout.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               setState(STATE_LOADING);
               clickFailedLayout();
           }
       });
        return mFailedLayout;
    }

    public View loadEmpty(){
        View mEmptyLayout = UIUtil.getDefaultFragmentLayout(this);
        mEmptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setState(STATE_LOADING);
                clickEmptyLayout();
            }
        });
        return mEmptyLayout;
    }
    public View loading(){
        return View.inflate(getContext(), R.layout.home_loading,null);
    }

    public void clickEmptyLayout(){
       Log.log(this.getClass().getSimpleName()+" clickEmptyLayout");
    }

    public void clickFailedLayout(){
        Log.log(this.getClass().getSimpleName()+"clickFailedLayout");
    }

    public StateManager getStateManager() {
        return mStateManager;
    }

    public TitleManager<TitleHolder> getTitleManager() {
        return mTitleManager;
    }
}
