package ru.fagci.bleplugin;

import android.app.Application;
import com.twofortyfouram.log.Lumberjack;

public class PluginApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Lumberjack.init(getApplicationContext());
    }
}
