package com.fuicuiedu.idedemo.easyshop.main.me.personInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fuicuiedu.idedemo.easyshop.R;
import com.fuicuiedu.idedemo.easyshop.commons.ActivityUtils;
import com.fuicuiedu.idedemo.easyshop.commons.RegexUtils;
import com.fuicuiedu.idedemo.easyshop.model.CachePreferences;
import com.fuicuiedu.idedemo.easyshop.model.User;
import com.fuicuiedu.idedemo.easyshop.model.UserResult;
import com.fuicuiedu.idedemo.easyshop.network.EasyShopClient;
import com.fuicuiedu.idedemo.easyshop.network.UICallBack;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/25.
 */

public class NickNameActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_nickname_error)
    TextView tvNicknameError;
    @BindView(R.id.et_nickname)
    EditText etNickname;


    private ActivityUtils activityUtils;
    private String str_nickname;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityUtils = new ActivityUtils(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.btn_save)
    public void onClick() {
        str_nickname =etNickname.getText().toString();
        //昵称不符合规则
        if (RegexUtils.verifyNickname(str_nickname)!=RegexUtils.VERIFY_SUCCESS){
            String msg= getString(R.string.nickname_rules);
            activityUtils.showToast(msg);
            return;
        }
        init();
    }
    //昵称修改
    private void init(){
        //从配置中拿到用户类
        User user= CachePreferences.getUser();
        //把要修改的昵称设置出来
        user.setNick_name(str_nickname);
        Call call= EasyShopClient.getInstance().uploadUser(user);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                activityUtils.showToast(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                UserResult userResult=new Gson().fromJson(body,UserResult.class);
                if (userResult.getCode()!=1){
                    activityUtils.showToast(userResult.getMessage());
                    return;
                }
                CachePreferences.setUser(userResult.getData());
                activityUtils.showToast("修改成功");
                //返回
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
