package com.eshel.ourvisa.titles;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshel.ourvisa.R;
import com.eshel.ourvisa.base.BaseTitleHolder;
import com.eshel.ourvisa.titles.clicklistener.BackClickListener;
import com.eshel.ourvisa.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * createBy Eshel
 * createTime: 2018/7/22 11:48
 * desc: 带有返回按钮的标题
 */
public class BackTitleHolder extends BaseTitleHolder<BackClickListener> implements View.OnClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.text_back)
    TextView textBack;

    public boolean backIsFinish = true;

    public BackTitleHolder(Context context) {
        super(context, R.layout.title_normal_has_back);
        ButterKnife.bind(this,mTitle);
        ViewUtil.setOnClickListener(this, back, textBack);
    }

    public BackTitleHolder setTitle(CharSequence title){
        this.title.setText(title);
        return this;
    }

    public BackTitleHolder setTitle(int titleId){
        this.title.setText(titleId);
        return this;
    }

    @Override
    public void onClick(View v) {
        if(back == v || textBack == v){
            if(mListener != null)
                mListener.clickBack();
            if(backIsFinish){
                try{
                    Activity act = ViewUtil.getActivity(back);
                    act.onBackPressed();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
