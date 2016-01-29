package com.example.greenplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FJS0420 on 2015/11/2.
 */
public class MainActivity extends FragmentActivity implements OnItemClickListener{
    /** 缓存视频列表 */
    private static ArrayList<String[]> mOnlineList = new ArrayList<>();
    /** 缓存视频LOGO列表 */
    private static ArrayList<Integer> mOnlineLogoList = new ArrayList<>();
    private WebView mWebView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list);
        SimpleAdapter adapter = new SimpleAdapter(  this,
                getData(),
                R.layout.fragment_online_item,
                new String[] {"name","img"},
                new int[]{R.id.title,R.id.thumbnail});
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    private List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        for(int i= 0; i < mOnlineList.size();i++){
            map = new HashMap<>();
            String[] str = new String[2];
            str = mOnlineList.get(i);
            map.put("name",str[0]);
            map.put("img",mOnlineLogoList.get(i));
            list.add(map);
        }

        return list;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
      static {
        // 120 60
        mOnlineList.add(new String[] { "优酷视频", "http://3g.youku.com" });
        mOnlineLogoList.add(R.mipmap.logo_youku);
        // 104 43
        mOnlineList.add(new String[] { "搜狐视频", "http://m.tv.sohu.com" });
        mOnlineLogoList.add(R.mipmap.logo_sohu);
        //
        mOnlineList.add(new String[] { "乐视TV", "http://m.letv.com" });
        mOnlineLogoList.add(R.mipmap.logo_letv);
        // 174 48
        mOnlineList.add(new String[] { "爱奇异", "http://3g.iqiyi.com/" });
        mOnlineLogoList.add(R.mipmap.logo_iqiyi);
        mOnlineList.add(new String[] { "PPTV", "http://m.pptv.com/" });
        mOnlineLogoList.add(R.mipmap.logo_pptv);
        // 181 60
        mOnlineList.add(new String[] { "腾讯视频", "http://3g.v.qq.com/" });
        mOnlineLogoList.add(R.mipmap.logo_qq);
        mOnlineList.add(new String[] { "56.com", "http://m.56.com/" });
        mOnlineLogoList.add(R.mipmap.logo_56);
        mOnlineList.add(new String[] { "新浪视频", "http://video.sina.cn/" });
        mOnlineLogoList.add(R.mipmap.logo_sina);
        mOnlineList.add(new String[] { "土豆视频", "http://m.tudou.com" });
        mOnlineLogoList.add(R.mipmap.logo_tudou);
        mOnlineList.add(new String[] { "工大在线", "http://online.njtech.edu.cn/" });
        mOnlineLogoList.add(R.mipmap.logo_online);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] f = mOnlineList.get(position);
        Intent intent = new Intent(MainActivity.this,FragmentOnline.class);
        Bundle bundle = new Bundle();
        bundle.putString("url",f[1]);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
