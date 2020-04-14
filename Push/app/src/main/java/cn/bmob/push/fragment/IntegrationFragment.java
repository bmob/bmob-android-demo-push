package cn.bmob.push.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.push.R;
import cn.bmob.push.bean.Installation;
import cn.bmob.push.bean.User;
import cn.bmob.push.ui.LoginActivity;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created on 17/8/24 16:29
 */

public class IntegrationFragment extends BaseFragment {
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_integration, null);
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

    @OnClick(R.id.btn_exit)
    public void onViewClicked() {

        modifyInstallationUser();

    }

    /**
     * 修改设备表的用户信息：先查询设备表中的数据，再修改数据中用户信息
     */
    private void modifyInstallationUser() {
        BmobQuery<Installation> bmobQuery = new BmobQuery<>();
        final String id = BmobInstallationManager.getInstallationId();
        bmobQuery.addWhereEqualTo("installationId", id);
        bmobQuery.findObjectsObservable(Installation.class)
                .subscribe(new Observer<List<Installation>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Installation> installations) {

                        if (installations.size() > 0) {
                            Installation installation = installations.get(0);
                            User user = new User();
                            installation.setUser(user);
                            user.setObjectId("");
                            installation.updateObservable()
                                    .subscribe(new Observer<BmobException>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(BmobException e) {

                                            toastI("更新设备用户信息成功！");
                                            /**
                                             * TODO 更新成功之后再退出
                                             */
                                            BmobUser.logOut();
                                            startActivity(new Intent(getContext(), LoginActivity.class));
                                            getActivity().finish();
                                        }

                                        @Override
                                        public void onError(Throwable throwable) {
                                            toastE("更新设备用户信息失败：" + throwable.getMessage());
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });

                        } else {
                            toastE("后台不存在此设备Id的数据，请确认此设备Id是否正确！\n" + id);
                        }


                    }

                    @Override
                    public void onError(Throwable throwable) {
                        toastE("查询设备数据失败：" + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
