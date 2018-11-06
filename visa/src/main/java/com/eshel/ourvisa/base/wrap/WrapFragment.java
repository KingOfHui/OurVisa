package com.eshel.ourvisa.base.wrap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshel.ourvisa.util.Log;

import butterknife.Unbinder;

public class WrapFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("Fragment_Life onCreate",getClass().getName()+" -- " + toString());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("Fragment_Life onCreateView",getClass().getName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        Log.e("Fragment_Life onDestroy",getClass().getName());
        super.onDestroy();
//        BaseApplication.getRefWatcher().watch(this);
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
