package cn.bmob.push.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * Created on 17/8/24 17:00
 */

public class BaseFragment extends Fragment {
    public Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
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
