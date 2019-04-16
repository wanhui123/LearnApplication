package com.wh.learnapplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.socks.library.KLog;
import com.wh.learnapplication.greenDao.db.DaoMaster;
import com.wh.learnapplication.greenDao.db.DaoSession;

import org.litepal.LitePal;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initGreenDao();
        LitePal.initialize(this);
        initKLog();

    }

    private void initKLog() {
        KLog.init(BuildConfig.DEBUG ,"kai");

    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "aserbao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
