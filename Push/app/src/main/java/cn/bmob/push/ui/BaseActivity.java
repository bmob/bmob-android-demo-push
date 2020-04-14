package cn.bmob.push.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * Created on 17/8/24 13:07
 */

public class BaseActivity extends AppCompatActivity {


    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public void toastI(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        Logger.i(msg);
    }

    public void toastE(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        Logger.e(msg);
    }
}
