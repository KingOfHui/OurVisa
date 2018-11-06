package com.eshel.ourvisa.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ViewUtil {

    /**
     * 批量设置控件是否显示
     */
    public static void setVisibility(int visibility, View... views){
        if(ObjUtil.notNull(views)){
            for (View view : views) {
                if(view != null){
                    view.setVisibility(visibility);
                }
            }
        }
    }

    /**
     * @param view 递归遍历view 及所有子view中所有TextView, 获取其text并替换为指定内容, 最后设置给textView
     */
    public static void replaceAllTextForTextView(View view, CharSequence target, CharSequence replacement){
        if(view != null){
            if(view instanceof ViewGroup){
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    replaceAllTextForTextView(((ViewGroup) view).getChildAt(i),target,replacement);
                }
            }else{
                if(view instanceof TextView){
                    CharSequence text = ((TextView) view).getText();
                    if(text != null){
                        String result = text.toString().replace(target, replacement);
                        ((TextView) view).setText(result);
                    }
                }
            }
        }
    }

    /**
     * 批量设置点击事件
     */
    public static void setOnClickListener(View.OnClickListener listener, View... views){
        if(views != null){
            for (View view : views) {
                if(view != null){
                    view.setOnClickListener(listener);
                }
            }
        }
    }

    /**
     * 设置按钮不可点击效果, 不可点击半透明效果
     */
    public static void setButtonClickable(Button button, boolean clickable){
        try {
            if(button != null){
                if(clickable){
                    button.setClickable(true);
                    button.getBackground().setAlpha(255);
                }else {
                    button.setClickable(false);
                    button.getBackground().setAlpha(80);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 根据 view 获取 Activity
     * @see ViewUtil#getActivity(Context)
     */
    public static Activity getActivity(View view) {
        if(view == null)
            return null;
        // Gross way of unwrapping the Activity so we can get the FragmentManager
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        new IllegalStateException("The "+view.getClass().getSimpleName()+"'s Context is not an Activity.").printStackTrace();
        return null;
    }

    /**
     * 有些 View 比如 EditText 会将其中的 Context 进行一层包装, 不能直接强转 Activity ,使用该方法进行强转
     */
    public static Activity getActivity(Context context) {
        // Gross way of unwrapping the Activity so we can get the FragmentManager
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        new IllegalStateException("The "+context.getClass().getName()+"'s is not an Activity.").printStackTrace();
        return null;
    }

    /**
     * 由于给 Drawable 设置透明度会导致其他界面使用同一图片的控件透明度也跟着改变, 使用该方法可以避免这个问题
     */
    public static void setDrawableAlpha(Drawable drawable, int alpha){
        if(ObjUtil.isNull(drawable))
            return;
        drawable.mutate().setAlpha(alpha);
    }

    /**
     * 更改 View 背景透明度, 避免同时更改其他界面使用相同资源的 View 透明度的问题
     */
    public static void setBackgroundAlpha(View view, int alpha){
        if(ObjUtil.isNull(view))
            return;

        Drawable background = view.getBackground();
        setDrawableAlpha(background, alpha);
    }
}
