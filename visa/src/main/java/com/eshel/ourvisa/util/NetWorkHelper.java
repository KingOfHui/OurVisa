package com.eshel.ourvisa.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

public class NetWorkHelper {
    /**
     * 在广播中使用 NetWorkUtil来判断网络状态
     * @param netWorketChangeListener 推荐使用匿名内部类创建
     */
    public static void registNetWorkChangeListener(Context context, BroadcastReceiver netWorketChangeListener){
        context.registerReceiver(netWorketChangeListener, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
}
