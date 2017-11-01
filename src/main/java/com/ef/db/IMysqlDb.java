package com.ef.db;

import com.ef.entity.Logger;

import java.util.List;

/**
 * Created by Swifta System on 10/1/2017.
 * Email: ${USER_EMAIL}
 * Phone: ${USER_PHONE}
 * Website: ${USER_WEBSITE}
 */
public interface IMysqlDb {

    public void init();

    public void print(List<Logger> records);
}
