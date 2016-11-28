package com.fuicuiedu.idedemo.easyshop.main.me.personInfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface PersonView extends MvpView {

    void showPrb();
    void hidePrb();
    void showMsg(String msg);

    //用来更新用户头像
    void updataAvatar(String url);
}
