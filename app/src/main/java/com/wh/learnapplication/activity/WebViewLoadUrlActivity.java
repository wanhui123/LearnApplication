package com.wh.learnapplication.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.socks.library.KLog;
import com.wh.learnapplication.R;
import com.wh.learnapplication.adapter.WebViewRvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewLoadUrlActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private WebView webView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_load_url);
        ButterKnife.bind(this);
        context = this;
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WebViewRvAdapter adapter = new WebViewRvAdapter(this);
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                webView = new WebView(getApplicationContext());
                webView.setFocusable(false);
//                ViewGroup.LayoutParams lp =new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//                webView.setLayoutParams(lp);
                webView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT));
                setupWebView();
                return webView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        recyclerView.setAdapter(adapter);
        for (int i = 0; i < 10; i++) {
            adapter.add("item  " + i);
        }
    }

    @SuppressLint("JavascriptInterface")
    private void setupWebView() {
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//启用js
        settings.setBlockNetworkImage(false);//解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setWebImageClick(view);//待网页加载完全后设置图片点击的监听方法
            }
        });
        webView.addJavascriptInterface(new JsCallJavaObj() {
            @JavascriptInterface
            @Override
            public void showBigImg(String url,int pos,String[] imgs) {
                KLog.d("图片路径= " + url + " pos =" + pos + "  size= " + imgs.length);
                Toast.makeText(WebViewLoadUrlActivity.this, "图片路径= " + url, Toast.LENGTH_SHORT).show();
            }
        }, "imagelistner");
        webView.loadUrl("https://m.gmw.cn/baijia/2019-04/12/1300296315.html");

    }

    /**
     * 设置网页中图片点击事件
     *
     * @param view
     */
    private void setWebImageClick(WebView view) {
        String jsCode = "javascript:(function(){" +
                "var imgs=document.getElementsByTagName(\"img\");" +
                "var array=new Array(); " +
                "for(var j=0;j<imgs.length;j++){ array[j]=imgs[j].src;}" +
                "for(var i=0;i<imgs.length;i++){" +
                "imgs[i].pos = i;" +
                "imgs[i].onclick=function(){" +
                "window.imagelistner.showBigImg(this.src,this.pos,array);" +
                "}}})()";
        webView.loadUrl(jsCode);
    }

    /**
     * Js调用Java接口
     */
    private interface JsCallJavaObj {
        void showBigImg(String url,int pos,String[] imgs);
    }


}
