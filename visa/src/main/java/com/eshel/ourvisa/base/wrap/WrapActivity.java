package com.eshel.ourvisa.base.wrap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;

/**
 * 对 AppCompatActivity 进行包装, 不修改任何生命周期方法, 预留后期统计使用
 */
@SuppressLint("Registered")
public class WrapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public final void unbind(Unbinder binder){
        if(binder != null){
            try {
                binder.unbind();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
