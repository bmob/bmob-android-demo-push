package cn.bmob.push.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.push.R;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.PushListener;

/**
 * Created on 17/8/24 16:29
 */

public class PushFragment extends BaseFragment {
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_push, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.btn_broadcast, R.id.btn_multi_cast_channel, R.id.btn_multi_cast_platform,
            R.id.btn_multi_cast_location, R.id.btn_multi_cast_active, R.id.btn_multi_cast_query, R.id.btn_uni_cast_android, R.id.btn_uni_cast_ios})
    public void onViewClicked(View view) {
        BmobPushManager bmobPushManager = new BmobPushManager();
        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
        switch (view.getId()) {
            case R.id.btn_broadcast://OK
                bmobPushManager.pushMessageAll("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Logger.e("推送成功！");
                        } else {
                            Logger.e("异常：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_channel:
                List<String> channels = new ArrayList<>();
                //TODO 替换成你需要推送的所有频道，推送前请确认已有设备订阅了该频道，也就是channels属性存在该值
                channels.add("NBA");
                query.addWhereContainedIn("channels", channels);
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Logger.e("推送成功！");
                        } else {
                            Logger.e("异常：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_platform://OK
                //TODO 属性值为android或者ios
                query.addWhereEqualTo("deviceType", "android");
                //query.addWhereEqualTo("deviceType", "ios");
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Logger.e("推送成功！");
                        } else {
                            Logger.e("异常：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_location://OK
                //TODO 替换你需要推送的地理位置的经纬度和范围，发送前请确认installation表中已有location的BmobGeoPoint类型属性
                query.addWhereWithinRadians("location", new BmobGeoPoint(113.385610000, 23.0561000000), 1.0);
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Logger.e("推送成功！");
                        } else {
                            Logger.e("发送前请确认installation表中已有location的BmobGeoPoint类型属性");
                            Logger.e("异常：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_active://OK
                //TODO 替换你需要的判断为不活跃的时间点
                query.addWhereLessThan("updatedAt", new BmobDate(new Date()));
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Logger.e("推送成功！");
                        } else {
                            Logger.e("异常：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_multi_cast_query://OK
                //TODO 替换成你作为判断需要推送的属性名和属性值，推送前请确认installation表已有该属性
                query.addWhereEqualTo("替换成你作为判断需要推送的属性名", "替换成你作为判断需要推送的属性值");
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Logger.e("推送成功！");
                        } else {
                            Logger.e("异常：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_uni_cast_android://OK
                //TODO 替换成所需要推送的Android客户端installationId
                String installationId = "替换成所需要推送的Android客户端installationId";
                query.addWhereEqualTo("installationId", installationId);
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Logger.e("推送成功！");
                        } else {
                            Logger.e("异常：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_uni_cast_ios://OK
                //TODO 替换成所需要推送的iOS客户端deviceToken
                String deviceToken = "";
                query.addWhereEqualTo("deviceToken", deviceToken);
                bmobPushManager.setQuery(query);
                bmobPushManager.pushMessage("消息内容", new PushListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Logger.e("推送成功！");
                        } else {
                            Logger.e("异常：" + e.getMessage());
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
