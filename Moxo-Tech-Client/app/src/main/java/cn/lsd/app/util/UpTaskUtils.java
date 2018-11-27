package cn.lsd.app.util;

import android.util.Log;

import cn.lsd.app.AppConstant;
import cn.lsd.app.model.uploadFile.UploadList;
import cn.lsd.app.model.uploadFile.UploadMsg;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LSD on 2017/5/12.
 */

public class UpTaskUtils {
    static List<UploadMsg> msgList = null;

    public List<UploadMsg> upTask(ArrayList<String> filePath) throws Exception {
        ArrayList<String> arrayList = filePath;
        List<UploadMsg> temp = null;
        //异步的客户端对象
        AsyncHttpClient client = new AsyncHttpClient();
        //指定url路径
        String url = AppConstant.UPLOAD_URL;
        //封装文件上传的参数
        RequestParams params = new RequestParams();

        for (int i = 0; i < arrayList.size(); i++) {
            File file = new File(arrayList.get(0).toString());
            try {
                params.put("profile_picture", file);
                //执行post请求
               temp = executePost(client, url, params);

            } catch (Exception e) {
                System.out.println("文件不存在----------");
            }

        }
        return temp;
    }

    public List<UploadMsg> executePost(AsyncHttpClient client, String url, RequestParams params) {
        msgList.clear();//这里在高并发的情况下存在隐患
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    //获取结果
                    try {
                        String result = new String(responseBody, "utf-8").toString();
                        Log.i("TAG", "------------------" + result);
                        Gson gson = new Gson();
                        UploadList ul = gson.fromJson(result, UploadList.class);
                        msgList = ul.getContent();
                        //Log.i("ShowResources","--------------"+msgList.toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
        return msgList;
    }
}
