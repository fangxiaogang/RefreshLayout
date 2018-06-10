package com.xiaogang.refreshlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private SmartRefreshLayout mSmartRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.msmartRefreshLayout);
        mSmartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(this));
        mSmartRefreshLayout.setEnableScrollContentWhenLoaded(true);
        mSmartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
    }
}
