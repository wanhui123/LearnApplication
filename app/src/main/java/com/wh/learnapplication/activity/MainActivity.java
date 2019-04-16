package com.wh.learnapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wh.learnapplication.R;
import com.wh.learnapplication.fragment.MyBottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_stepProgressView)
    TextView tvStepProgressView;
    @BindView(R.id.tv_GreenDao)
    TextView tvGreenDao;
    @BindView(R.id.tv_BottomSheetDialogFragment)
    TextView tv_BottomSheetDialogFragment;
    @BindView(R.id.tv_HtmlSpanner)
    TextView tv_HtmlSpanner;
    @BindView(R.id.tv_rxJava2)
    TextView tvRxJava2;
    @BindView(R.id.tv_banner)
    TextView tvBanner;
    @BindView(R.id.tv_ChannelView)
    TextView tvChannelView;
    @BindView(R.id.tv_webView)
    TextView tvWebView;
    @BindView(R.id.tv_webViewLoadUrl)
    TextView tvWebViewLoadUrl;
    private MyBottomSheetDialogFragment myBottomSheetDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_stepProgressView, R.id.tv_GreenDao, R.id.tv_BottomSheetDialogFragment, R.id.tv_HtmlSpanner, R.id.tv_rxJava2,
            R.id.tv_banner, R.id.tv_ChannelView, R.id.tv_webView, R.id.tv_webViewLoadUrl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_stepProgressView:
                startActivity(new Intent(MainActivity.this, StepprogressviewActivity.class));
                break;
            case R.id.tv_GreenDao:
                startActivity(new Intent(MainActivity.this, GreenDaoActivity.class));
                break;
            case R.id.tv_BottomSheetDialogFragment:
                if (myBottomSheetDialogFragment == null) {
                    myBottomSheetDialogFragment = new MyBottomSheetDialogFragment();
                }
                myBottomSheetDialogFragment.show(getSupportFragmentManager(), null);
                break;
            case R.id.tv_HtmlSpanner:
                startActivity(new Intent(MainActivity.this, HtmlSpannerActivity.class));
                break;
            case R.id.tv_rxJava2:
                startActivity(new Intent(MainActivity.this, RxJava2Activity.class));
                break;
            case R.id.tv_banner:
                startActivity(new Intent(MainActivity.this, BannerActivity.class));
                break;
            case R.id.tv_ChannelView:
                startActivity(new Intent(MainActivity.this, ChannelViewActivity.class));
                break;
            case R.id.tv_webView:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;
            case R.id.tv_webViewLoadUrl:
                startActivity(new Intent(MainActivity.this, WebViewLoadUrlActivity.class));
                break;
        }
    }
}
