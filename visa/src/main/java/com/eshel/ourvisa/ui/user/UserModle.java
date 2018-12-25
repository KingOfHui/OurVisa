package com.eshel.ourvisa.ui.user;

import android.content.SharedPreferences;

import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.bean.net.User;
import com.eshel.ourvisa.database.table.UserTable;
import com.eshel.ourvisa.mvp.base.Modle;
import com.eshel.ourvisa.mvp.modles.ConfigModle;
import com.eshel.ourvisa.mvp.modles.IUserModle;
import com.eshel.ourvisa.mvp.modles.modlecallback.IUserModleCallback;
import com.eshel.ourvisa.util.ObjUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class UserModle extends Modle<IUserModleCallback> implements IUserModle {

    private User mUser;
    private final ConfigModle mConfig;

    public UserModle(IUserModleCallback callback) {
        super(callback);
        mConfig = VisaApp.getContext().getConfig();
    }

    @Override
    protected void onClose() {
        mUser = null;
    }

    @Override
    public synchronized User getUser() {
        if(mUser == null) {
            long userId = mConfig.getUserConfig().getLong(User.USER_ID, 0);
            try {
                Dao<UserTable, Long> dao = mConfig.getDatabaseHelper().getDao(UserTable.class);
                UserTable userTable = dao.queryForId(userId);
                if (ObjUtil.notNull(userTable)) {
                    String json = userTable.userInfo;
                    mUser = mConfig.getGson().fromJson(json, User.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mUser;
    }

    @Override
    public void saveUser(User user) {
        ObjUtil.checkNull(user);
        try {
            SharedPreferences.Editor editor = mConfig.getUserConfig().edit().putLong(User.USER_ID, user.userId);
            UserTable userTable = new UserTable(user.userId, mConfig.getGson().toJson(user));
            Dao<UserTable, Long> dao = mConfig.getDatabaseHelper().getDao(UserTable.class);
            UserTable oldUserTable = dao.queryForId(userTable.userId);
            if(oldUserTable != null)
                dao.update(userTable);
            else
                dao.create(userTable);
            editor.apply();
            mUser = user;
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
