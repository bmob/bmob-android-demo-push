package cn.bmob.push.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;

import cn.bmob.push.R;
import cn.bmob.push.bean.User;
import cn.bmob.v3.BmobUser;

/**
 * Created on 17/8/24 12:51
 */

public class FlashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = BmobUser.getCurrentUser(User.class);
                if (user == null) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                } else {
                    startActivity(new Intent(mContext, MainActivity.class));
                }
            }
        }, 1000);
    }
}
