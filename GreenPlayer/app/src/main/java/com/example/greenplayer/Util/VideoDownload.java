package com.example.greenplayer.Util;


import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by FJS0420 on 2016/1/28.
 */
public class VideoDownload {

    //获取Vid
    public static String getVid(String address) {
        String strRegex = "(?<=id_)(\\w+)";
        Pattern p = Pattern.compile(strRegex);
        Matcher m = p.matcher(address);
        String vid = "";
        if(m.find()) {
            vid = m.group(0);
        }
        return vid;
    }

    //获取json地址
    public static String getsource_ad(String address){
        return "http://play.youku.com/play/get.json?vid=" + getVid(address) +"&ct=12";
    }

    //获取json
    public static String getJsonContent(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(3000);
            connection.setDoInput(true);
            int code = connection.getResponseCode();
            if (code == 200) {
                return changeInputString(connection.getInputStream());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    private static String changeInputString(InputStream inputStream) {

        String jsonString = "";
        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                outPutStream.write(data, 0, len);
            }
            jsonString = new String(outPutStream.toByteArray(), "UTF-8");
            outPutStream.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonString;
    }

    //获取ep和oip
    public static List<String> getEp_and_oip(String json){
        String ep ;
        String oip ;
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject object = jsonObject.getJSONObject("data");
            JSONObject obj1 = object.getJSONObject("security");
            ep = obj1.getString("encrypt_string");
            oip = obj1.getString("ip");
            list.add(ep);
            list.add(oip);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            Log.i("toURLEncoded error0:","toURLEncoded error:" + paramString);
            return "";
        }

        try
        {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        }
        catch (Exception localException)
        {
            Log.e("toURLEncoded error1:","toURLEncoded error:" + paramString, localException);
        }

        return "";
    }

    private static String myEncoder(String a, byte[] c, boolean isToBase64)
    {
        String result = "";
        List<Byte> bytesR = new ArrayList<Byte>();
        int f = 0, h = 0, q = 0;
        int[] b = new int[256];
        for (int i = 0; i < 256; i++)
            b[i] = i;
        while (h < 256)
        {
            f = (f + b[h] + (byte)a.charAt(h % a.length())) % 256;
            int temp = b[h];
            b[h] = b[f];
            b[f] = temp;
            h++;
        }
        f = 0; h = 0; q = 0;
        while (q < c.length)
        {
            h = (h + 1) % 256;
            f = (f + b[h]) % 256;
            int temp = b[h];
            b[h] = b[f];
            b[f] = temp;
            byte[] bytes = new byte[] { (byte)(c[q] ^ b[(b[h] + b[f]) % 256]) };
            bytesR.add(bytes[0]);
            try {
                result += new String(bytes,"ASCII");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            q++;
        }
        if (isToBase64)
        {
            byte[] byteR =  bytesR.toString().getBytes();
            result = String.valueOf(Base64.encode(byteR, Base64.DEFAULT));
        }
        return result;
    }

    //获取 0：sid 1：token 2：epNew
    public static List<String> getParameters(String vid, String ep)
    {

        String epNew, token,sid;
        List<String> list = new ArrayList<>(); //存放 0：sid 1：token 2：epNew
        String template1 = "becaf9be";
        String template2 = "bf7e5f01";
        byte[] bytes= Base64.decode(ep, Base64.DEFAULT);
        try {
            ep = new String(bytes,"ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String temp = myEncoder(template1, bytes, false);
        String[] part = temp.split("_");
        sid = part[0];
        token = part[1];
        String whole = String.format("%s_%s_%s", sid, vid, token); //String.format是用来连接字符串 %s是字符串的占位符
        byte[] newbytes = new byte[0];
        try {
            newbytes = whole.getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        epNew = myEncoder(template2, newbytes, true);
        list.add(sid);
        list.add(token);
        list.add(epNew);
        return list;
    }
}
