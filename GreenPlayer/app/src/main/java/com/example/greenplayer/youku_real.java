package com.example.greenplayer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.greenplayer.Util.VideoDownload;

import java.util.List;

/**
 * Created by FJS0420 on 2016/1/29.
 */
public class youku_real extends Activity {
    private TextView tv_real;
    String address = "http://v.youku.com/v_show/id_XMTQ0NTc5NTc2OA==.html?from=y1.3-movie-grid-1095-9921.86994-86993.1-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youku_ad);
        tv_real = (TextView) findViewById(R.id.tv_real_ad);
        new Thread(network).start();

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("mylog", "请求结果为-->" + val);
            // TODO
            // UI界面的更新等相关操作
            tv_real.setText(val);
        }
    };


    Runnable network = new Runnable() {
        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            Message msg = new Message();
            Bundle data = new Bundle();

            String vid = VideoDownload.getVid(address);
            String source_ad = VideoDownload.getsource_ad(address);
            String json = VideoDownload.getJsonContent(source_ad);
            List<String> list_ep_and_oip = VideoDownload.getEp_and_oip(json);
            System.out.println("============================="+list_ep_and_oip.size());
            String ep = list_ep_and_oip.get(0);
            String oip = list_ep_and_oip.get(1);
            List<String> parameters = VideoDownload.getParameters(vid, ep);
            String sid = parameters.get(0);
            String token = parameters.get(1);
            String real_ep = VideoDownload.toURLEncoded(parameters.get(2));
            String real_address = "http://pl.youku.com/playlist/m3u8?ctype=12&ep="+real_ep+"&ev=1&keyframe=1&oip="+oip+"&sid="+sid+"&token="+token+"&type=mp4&vid="+vid;
            data.putString("value", real_address);
            msg.setData(data);
            handler.sendMessage(msg);
        }

    };
}
