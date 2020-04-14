package cn.bmob.push.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.push.R;
import cn.bmob.push.bean.Installation;
import cn.bmob.push.bean.User;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A login screen that offers login via account/password.
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_progress)
    ProgressBar mLoginProgress;
    @BindView(R.id.account)
    AutoCompleteTextView mAccount;
    @BindView(R.id.password)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.account_sign_in_button, R.id.account_sign_up_button})
    public void onViewClicked(View view) {
        String account = mAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            toastE("账号不能为空！");
            return;
        }
        String password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            toastE("密码不能为空！");
            return;
        }
        User user = new User();
        switch (view.getId()) {
            case R.id.account_sign_in_button:
                user.setUsername(account);
                user.setPassword(password);
                Logger.i(account + "\n" + password);
                user.loginObservable(User.class)

                        .subscribe(new Observer<User>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(User user) {

                                startActivity(new Intent(mContext, MainActivity.class));
                                toastI("登录成功！");
                                modifyInstallationUser(user);
                            }

                            @Override
                            public void onError(Throwable throwable) {

                                toastE("登录异常：" + throwable.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case R.id.account_sign_up_button:
                user.setUsername(account);
                user.setPassword(password);
                user.signUpObservable(User.class)
                        .subscribe(new Observer<User>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(User user) {

                                startActivity(new Intent(mContext, MainActivity.class));
                                toastI("注册成功！");
                            }

                            @Override
                            public void onError(Throwable throwable) {

                                toastE("注册异常：" + throwable.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;


            default:
                break;
        }
    }

    /**
     * 修改设备表的用户信息：先查询设备表中的数据，再修改数据中用户信息
     *
     * @param user
     */
    private void modifyInstallationUser(final User user) {
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
                            installation.setUser(user);
                            installation.updateObservable()
                                    .subscribe(new Observer<BmobException>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(BmobException e) {

                                            toastI("更新设备用户信息成功！");
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

