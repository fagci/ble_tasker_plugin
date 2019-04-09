package ru.fagci.bleplugin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import ru.fagci.bleplugin.R;

public class InfoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
