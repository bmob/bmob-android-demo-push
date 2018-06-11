package cn.bmob.push.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.push.R;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created on 17/8/24 16:29
 */

public class ChannelFragment extends BaseFragment {
    @BindView(R.id.tv_channel)
    TextView mTvChannel;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_channel, null);
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

    @OnClick({R.id.btn_subscribe, R.id.btn_unsubscribe, R.id.btn_channels})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_subscribe:
                BmobInstallationManager.getInstance().subscribe(Arrays.asList("NBA", "CBA", "NCAA", "NBA", "CBA"), new InstallationListener<BmobInstallation>() {
                    @Override
                    public void done(BmobInstallation bmobInstallation, BmobException e) {
                        if (e == null) {
                            toastI("批量订阅成功");
                        } else {
                            toastE(e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_unsubscribe:

                BmobInstallationManager.getInstance().unsubscribe(Arrays.asList("CBA", "NCAA"), new InstallationListener<BmobInstallation>() {
                    @Override
                    public void done(BmobInstallation bmobInstallation, BmobException e) {
                        if (e == null) {
                            toastI("批量取消订阅成功");
                        } else {
                            toastE(e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_channels:
                BmobInstallation bmobInstallation = BmobInstallationManager.getInstance().getCurrentInstallation();
                List<String> channels = bmobInstallation.getChannels();
                if (channels.size() < 1) {
                    toastI("您没有订阅任何频道！");
                } else {
                    for (String channel : channels) {
                        mTvChannel.append(channel + "\n");
                    }
                }
                break;

            default:
                break;
        }
    }
}
