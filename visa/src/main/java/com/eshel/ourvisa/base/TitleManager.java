package com.eshel.ourvisa.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.util.DensityUtil;


/**
 * createBy Eshel
 * createTime: 2018/7/21 21:38
 * desc: 将title and main layout 组合在一起
 */
public class TitleManager<TitleHolder extends BaseTitleHolder>{
    /**
     * title 在上, content 在 title 下方
     */
    public static final int MODE_VERTICAL = -1;
    /**
     * title 和 content 层叠显示, 优先显示title
     */
    public static final int MODE_STACK = 0;

    private int mode;
    private RelativeLayout mRoot;
    private FrameLayout mContent;
    private FrameLayout mTitleLayout;
    private TitleHolder mTitle;

    public TitleManager(TitleHolder title, Context context, int mode) {
        this.mode = mode;
        this.mTitle = title;
        mRoot = new RelativeLayout(context);
        mRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if(title != null && title.useCardView) {
            mTitleLayout = new CardView(context);

            ((CardView)mTitleLayout).setCardElevation(title.elevation);
        } else
            mTitleLayout = new FrameLayout(context);
        mContent = new FrameLayout(context);
//        unUseCardView();
        mTitleLayout.setId(R.id.title_parent_id);
        if(mTitle != null && mTitle.mTitle != null) {
            mTitleLayout.addView(mTitle.mTitle);
        }
        RelativeLayout.LayoutParams contentParm = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams titleParm = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRoot.addView(mContent,contentParm);
        mRoot.addView(mTitleLayout, titleParm);
        setMode(mode);
    }

    public View getContentView() {
        return mContent.getChildAt(0);
    }

    public void setTitleHolder(TitleHolder holder){
        this.mTitle = holder;
        if(mTitle != null && mTitle.mTitle != null) {
            mTitleLayout.removeAllViews();
            mTitleLayout.addView(mTitle.mTitle);
        }
    }

    public TitleHolder getTitleHolder(){
        return mTitle;
    }

    public void setMode(@IntRange(from = MODE_VERTICAL, to = MODE_STACK) int mode){
        if(mRoot.getChildCount() > 1 && mTitle != null && mTitle.mTitle != null){
            this.mode = mode;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mContent.getLayoutParams();
            if(layoutParams != null){
                switch (mode){
                    case MODE_VERTICAL:
                        layoutParams.addRule(RelativeLayout.BELOW,mTitleLayout.getId());
                        break;
                    case MODE_STACK:
                        layoutParams.addRule(RelativeLayout.BELOW, 0);
                        break;
                }
            }
        }
    }

    /**
     * 不使用CardView
     */
    public void unUseCardView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mTitleLayout.setBackground(new ColorDrawable(Color.TRANSPARENT));
        }else {
            mTitleLayout.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    /**
     * 与 StateManager 一起使用
     * @param stateManager
     */
    public void bindToStateManager(StateManager stateManager){
        stateManager.initState(mContent);
    }

    /**
     * 单独使用 TitleManager 调用 setContentView 方法来设置内容布局
     * @param resId
     */
    public void setContentView(int resId){
        View.inflate(mContent.getContext(),resId,mContent);
    }

    /**
     * @see TitleManager#setContentView(int)
     * @param view
     */
    public void setContentView(View view){
        mContent.addView(view);
    }

    /**
     * @see TitleManager#setContentView(int)
     * @param view
     * @param params
     */
    public void setContentView(View view, FrameLayout.LayoutParams params){
        mContent.addView(view,params);
    }

    /**
     * TitleManager 初始化方法
     * @return 只有将init() 方法返回的view添加到 Activity 或者 Fragment上, 才能正常显示标题
     */
    public View init(){
        return mRoot;
    }
}
