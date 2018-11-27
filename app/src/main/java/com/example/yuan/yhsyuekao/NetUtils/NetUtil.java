package com.example.yuan.yhsyuekao.NetUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetUtil {
    //单例
    private static NetUtil instance;

    public NetUtil() {
    }

    public static NetUtil getInstance() {
        if (instance == null) {
            instance = new NetUtil();
        }
        return instance;
    }

    //定义接口
    public interface CallBack<T> {
        void onSuccess(T t);
    }

    //请求数据
    public static String getData(String strUrl) {
        String result = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置读取,超时
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            int responseCode = httpURLConnection.getResponseCode();
            //判断状态码
            if (responseCode == 200) {
                result = stream2String(httpURLConnection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String stream2String(InputStream inputStream) throws IOException {
        //读取流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        //循环读取
        for (String t = reader.readLine(); t != null; t = reader.readLine()) {
            stringBuilder.append(t);
        }
        return stringBuilder.toString();
    }

    //json
    public static <T> T getData(String strUrl, Class clazz) {
        return (T) new Gson().fromJson(getData(strUrl), clazz);
    }

    //接口
    public static void getData(final String strUrl, final Class clazz, final CallBack callBack) {
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... strings) {
                return getData(strUrl, clazz);
            }

            @Override
            protected void onPostExecute(Object o) {
                callBack.onSuccess(o);
            }
        }.execute(strUrl);
    }

    //网络判断
    public static boolean isPanduan(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

}
