package com.eshel.ourvisa.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshel.ourvisa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyItemView extends FrameLayout {
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.title)
    TextView title;

    public MyItemView(@NonNull Context context) {
        this(context, null);
    }

    public MyItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.view_my_item, this);
        ButterKnife.bind(this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyItemView);
        String title = array.getString(R.styleable.MyItemView_item_title);
        int iconId = array.getResourceId(R.styleable.MyItemView_item_icon, 0);
        array.recycle();
        if(iconId != 0){
            icon.setImageResource(iconId);
        }
        this.title.setText(title);
    }
}
