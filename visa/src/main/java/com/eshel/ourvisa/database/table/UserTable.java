package com.eshel.ourvisa.database.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "UserTable")
public class UserTable {
    @DatabaseField(id = true)
    public long userId;

    /**
     * Json 格式
     */
    @DatabaseField(columnName = "userInfo")
    public String userInfo;

    public UserTable(long userId, String userInfo) {
        this.userId = userId;
        this.userInfo = userInfo;
    }
}
