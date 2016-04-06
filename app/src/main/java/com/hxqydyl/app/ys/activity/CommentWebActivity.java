package com.hxqydyl.app.ys.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.hxqydyl.app.ys.utils.Constants;

import framework.BaseFragmentActivity;

/**
 * Created by admin on 2016/4/6.
 */
public class CommentWebActivity extends BaseWebActivity implements BaseWebActivity.OnLoginSuccess {
    private String url;
    private String title;

    //应该需要传所需的方法对象
    public static void toCommentWeb(String url, String title, FragmentActivity a, boolean isNeedLogin) {
        Intent intent = new Intent(a, CommentWebActivity.class);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra("title", title);
        }
        intent.putExtra("isNeedLogin", isNeedLogin);
        intent.putExtra("url", url);
        a.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");

        } else {
            WebChromeClient wvcc = new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    initViewOnBaseTitle(title);
                }
            };
            webView.setWebChromeClient(wvcc);
        }
        url = getIntent().getStringExtra("url");
        setIsNeedLogin(getIntent().getBooleanExtra("isNeedLogin", false), this);
        loadUrl(url);
    }

    @Override
    public void onLoginSuccess() {
        loadUrl(url);
    }

    @Override
    public void onLoginfail() {
        this.finish();
    }
}