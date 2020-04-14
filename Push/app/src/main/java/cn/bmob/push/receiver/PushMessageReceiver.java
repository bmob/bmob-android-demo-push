package cn.bmob.push.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import cn.bmob.push.PushConstants;
import cn.bmob.push.R;
import cn.bmob.push.ui.MainActivity;
import cn.bmob.v3.util.BmobNotificationManager;

/**
 * Created on 17/8/24 14:20
 */

//TODO 集成：1.3、创建自定义的推送消息接收器，并在清单文件中注册
public class PushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg = intent.getStringExtra("msg");
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Logger.i("客户端收到推送消息：" + msg);

            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            BmobNotificationManager.showNotification(context, R.mipmap.ic_launcher,"客户端收到推送", msg, msg, pendingIntent);

        }


    }
}
