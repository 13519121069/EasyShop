package com.fuicuiedu.idedemo.easyshop.main.me;


import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.fuicuiedu.idedemo.easyshop.R;
import com.fuicuiedu.idedemo.easyshop.commons.ActivityUtils;
import com.fuicuiedu.idedemo.easyshop.components.AvatarLoadOptions;
import com.fuicuiedu.idedemo.easyshop.main.me.personInfo.PersonActivity;
import com.fuicuiedu.idedemo.easyshop.model.CachePreferences;
import com.fuicuiedu.idedemo.easyshop.network.EasyShopApi;
import com.fuicuiedu.idedemo.easyshop.user.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    @BindView(R.id.iv_user_head)
    ImageView iv_user_head;
    @BindView(R.id.tv_login)
    TextView tv_login;

    private View view;
    private ActivityUtils activityUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(CachePreferences.getUser().getName()==null)return;
        if (CachePreferences.getUser().getNick_name()==null){
            tv_login.setText("请输入昵称");

        }else{
            tv_login.setText(CachePreferences.getUser().getNick_name());
        }
        ImageLoader.getInstance()
                .displayImage(EasyShopApi.IMAGE_URL+CachePreferences.getUser().getHead_Image(),iv_user_head, AvatarLoadOptions.build());

    }

    @OnClick({R.id.iv_user_head, R.id.tv_person_info, R.id.tv_login, R.id.tv_person_goods, R.id.tv_goods_upload})
    public void onClick(View  view) {
      if (CachePreferences.getUser().getName()==null){
          activityUtils.startActivity(LoginActivity.class);
          return;
      }
        switch (view.getId()){
            case R.id.iv_user_head:
            case R.id.tv_login:
            case R.id.tv_person_info:
                activityUtils.startActivity(PersonActivity.class);
                break;
            case R.id.tv_person_goods:
                activityUtils.showToast("我的商品，待实现");
                break;
            case R.id.tv_goods_upload:
                activityUtils.showToast("商品上传，待实现");
                break;
        }
    }
}
