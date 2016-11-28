package com.fuicuiedu.idedemo.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/11/21.
 */

public class UserResult  {
    private int code;

    @SerializedName("msg")
    private String message;

    private User data;

    //alt + insert

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }
}
