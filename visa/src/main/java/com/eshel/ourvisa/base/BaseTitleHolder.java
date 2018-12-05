package com.eshel.ourvisa.base;

import android.content.Context;
import android.view.View;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.titles.clicklistener.BaseClickListener;
import com.eshel.ourvisa.util.DensityUtil;
import com.eshel.ourvisa.util.UIUtil;

/**
 * createBy Eshel
 * createTime: 2018/7/21 21:38
 * desc: 标题 Holder 基类, 对标题布局进行封装管理, 复用 BaseTitleHolder 即可复用标题
 */
public abstract class BaseTitleHolder<ClickListener extends BaseClickListener>{

    boolean useCardView;
    int elevation = DensityUtil.dp2px(3);
    protected View mTitle;

    public<T extends BaseTitleHolder> T setUseCardView(boolean useCardView){
        this.useCardView = useCardView;
        return (T) this;
    }

    public <T extends BaseTitleHolder> T setElevation(int elevation){
        this.elevation = elevation;
        return (T) this;
    }

    public BaseTitleHolder(Context context, int layoutId){
        this(View.inflate(context,layoutId,null));
    }

    public BaseTitleHolder(View mTitle) {
        this.mTitle = mTitle;
        if(mTitle != null) {
            View stateBarEmpty = mTitle.findViewById(R.id.state_bar_empty);
            if(stateBarEmpty != null){
                UIUtil.setViewHeight(stateBarEmpty,UIUtil.getStateBarHeight());
            }
        }
    }

    protected ClickListener mListener;

    public void setClickListener(ClickListener listener){
        mListener = listener;
    }

    public ClickListener removeClickListener(){
        ClickListener temp = mListener;
        mListener = null;
        return temp;
    }

    public View getTitleView() {
        return mTitle;
    }

    public ClickListener getListener() {
        return mListener;
    }
}
