package com.eshel.ourvisa.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * createBy Eshel
 * createTime: 2018/7/21 20:27
 * desc: Activity Fragment 等类的状态标致, 成功 失败 加载中 空视图 以及其他附加状态
 */
public class State {
    public static final int STATE_FAILED = -1;//加载失败
    public static final int STATE_LOADING = 0;//加载中
    public static final int STATE_SUCCESS = 1;//加载成功
    public static final int STATE_EMPTY = 2;//空视图
    static State findStateByCode(List<State> states, int code){
        if(states != null){
            for (State state : states) {
                if(state != null && state.stateCode == code)
                    return state;
            }
        }
        return null;
    }

    public State(int stateCode, View stateView) {
        this.stateCode = stateCode;
        this.stateView = stateView;
    }

    /**
     * 与  STATE_LOADING 同等, 不能与已有状态重复
     */
    public int stateCode;
    /**
     * 某状态要加载的View对象
     */
    public View stateView;
    /**
     * stateView 添加至父类参数
     */
    public ViewGroup.LayoutParams stateViewParames;
    /**
     * stateView 和 layoutId 二选一
     */
    public int layoutId;
}