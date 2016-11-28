package com.fuicuiedu.idedemo.easyshop.network;

import com.fuicuiedu.idedemo.easyshop.model.CachePreferences;
import com.fuicuiedu.idedemo.easyshop.model.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class EasyShopClient {

    private static EasyShopClient easyShopClient;

    private OkHttpClient okHttpClient;
    private Gson  gson;

    private EasyShopClient() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                //用于添加bomb的请求头拦截器
                .addInterceptor(interceptor)
                //日志拦截器
                .addInterceptor(interceptor)
                .build();
        gson=new Gson();
    }

    public static EasyShopClient getInstance() {
        if (easyShopClient == null) {
            easyShopClient = new EasyShopClient();
        }
        return easyShopClient;
    }

    /**
     * 注册
     * <p>
     * post
     *
     * @param username 用户名
     * @param password 密码
     */
    public Call register(String username, String password) {
        //表单形式构建请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        //构建请求
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.REGISTER)
                .post(requestBody) //ctrl+p查看参数
                .build();

        return okHttpClient.newCall(request);
    }
    /**
     * 登录
     * <p>
     * post
     *
     * @param username 用户名
     * @param password 密码
     */
    public Call login(String username, String password) {
        //表单形式构建请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        //构建请求
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.LOGIN)
                .post(requestBody) //ctrl+p查看参数
                .build();

        return okHttpClient.newCall(request);
    }
    /**
     * 更换昵称
     * <p>
     *     post
     *
     *     @param user 用户实体类
     */
    public Call uploadUser(User user){
        //多部分形式构建请求体
        RequestBody requestBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user",gson.toJson(user))
                .build();
        //构建请求
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.UPDATA)
                .post(requestBody)//ctrl+p查看参数
                .build();
        return okHttpClient.newCall(request);
    }

    /**
     * 更换头像
     * <p>
     *  post
     * @param file  要更新的头像文件
     * @return
     */
    public Call uploadAvatar(File file){
        //多部分形式构建请求体
        RequestBody requestBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user",gson.toJson(CachePreferences.getUser()))
                .addFormDataPart("image",file.getName(),RequestBody.create(MediaType.parse("inage/png"),file))
                .build();
        //构建请求
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.UPDATA)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request);
    }



}
