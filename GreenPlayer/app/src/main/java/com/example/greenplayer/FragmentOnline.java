package com.example.greenplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by FJS0420 on 2015/11/1.
 */
public class FragmentOnline extends Activity {


    private WebView mWebView;
    private TextView textView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_online);
        mWebView = (WebView) findViewById(R.id.webview);
        //textView = (TextView) findViewById(R.id.te);
        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");
       // textView.setText(url);
        mWebView.loadUrl(url);
        mWebView.setVisibility(View.VISIBLE);
        initWebView();
    }

    //初始化webview
    private void initWebView() {
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (com.example.greenplayer.Util.FileUtils.isVideoOrAudio(url)) {
                    Intent intent = new Intent(FragmentOnline.this, PlayActivity.class);
                    intent.putExtra("path", url);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView != null
                        && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });
    }


}
