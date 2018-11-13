package com.eshel.ourvisa.base.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
    private boolean noScroll = true;
  
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }
  
    public NoScrollViewPager(Context context) {
        super(context);  
    }  
  
    public void setNoScroll(boolean noScroll) {  
        this.noScroll = noScroll;  
    }  
  
    @Override
    public void scrollTo(int x, int y) {  
        super.scrollTo(x, y);  
    }  
  
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */  
        if (noScroll)  
            return false;  
        else  
            return super.onTouchEvent(arg0);  
    }  
  
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)  
            return false;  
        else  
            return super.onInterceptTouchEvent(arg0);  
    }  
  
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {  
        super.setCurrentItem(item, smoothScroll);  
    }
  
    @Override
    public void setCurrentItem(int item) {  
        super.setCurrentItem(item);  
    }

    public void setOffscreenPageLimit(int limit) {
        /*try {
            if(limit < 0 )
                limit = 0;
            Field field = ViewPager.class.getDeclaredField("mOffscreenPageLimit");
            field.setAccessible(true);
            int mOffscreenPageLimit = field.getInt(this);
            if (limit != mOffscreenPageLimit) {
                field.set(this,limit);
                Method method = ViewPager.class.getDeclaredMethod("populate");
                method.setAccessible(true);
                method.invoke(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        super.setOffscreenPageLimit(limit);
    }
}