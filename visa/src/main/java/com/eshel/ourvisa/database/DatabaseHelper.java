package com.eshel.ourvisa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.eshel.ourvisa.VisaApp;
import com.eshel.ourvisa.database.table.UserTable;
import com.eshel.ourvisa.mvp.modles.ConfigModle;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "Visa.db";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          ConnectionSource connectionSource, int oldVersion, int newVersion) {
//            todo 数据库升级, 遍历数据库所有数据重新转移到新版本
//            TableUtils.dropTable(connectionSource, DiskCacheTable.class, true);
    }

    /**
     * 单例获取该Helper
     */
    public static DatabaseHelper getInstance() {
        VisaApp context = VisaApp.getContext();
        ConfigModle config = context.getConfig();
        return config.getDatabaseHelper();
    }

    public static DatabaseHelper createDatabaseHelper(Context context){
        return new DatabaseHelper(context.getApplicationContext());
    }


    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
    }
}