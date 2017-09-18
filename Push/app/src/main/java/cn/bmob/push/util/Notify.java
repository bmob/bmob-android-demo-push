package cn.bmob.push.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import org.json.JSONObject;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

/**
 * Created on 17/6/21 15:34
 */

public class Notify {


    private Context mContext;
    private NotificationManager mManager;
    private Integer mId = null;
    private CharSequence mTitle = null;
    private CharSequence mText = null;
    private Integer mSmallIcon = null;
    private Bitmap mLargeIcon = null;
    private CharSequence mSubText = null;
    private Boolean mAutoCancel = null;
    private Uri mSound;
    private PendingIntent mPendingIntent = null;

    public Notify(Context context) {
        mContext = context;
        mManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public PendingIntent getPendingIntent() {
        return mPendingIntent;
    }

    public Notify setPendingIntent(PendingIntent pendingIntent) {
        mPendingIntent = pendingIntent;
        return this;
    }

    public Integer getId() {
        return mId;
    }

    public Notify setId(Integer id) {
        mId = id;
        return this;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public Notify setTitle(CharSequence title) {
        mTitle = title;
        return this;
    }

    public CharSequence getText() {
        return mText;
    }

    public Notify setText(CharSequence text) {
        mText = text;
        return this;
    }

    public Integer getSmallIcon() {
        return mSmallIcon;
    }

    public Notify setSmallIcon(Integer smallIcon) {
        mSmallIcon = smallIcon;
        return this;
    }

    public Bitmap getLargeIcon() {
        return mLargeIcon;
    }

    public Notify setLargeIcon(Bitmap largeIcon) {
        mLargeIcon = largeIcon;
        return this;
    }

    public CharSequence getSubText() {
        return mSubText;
    }

    public Notify setSubText(CharSequence subText) {
        mSubText = subText;
        return this;
    }

    public Boolean getAutoCancel() {
        return mAutoCancel;
    }

    public Notify setAutoCancel(Boolean autoCancel) {
        mAutoCancel = autoCancel;
        AsyncCustomEndpoints asyncCustomEndpoints = new AsyncCustomEndpoints();

        asyncCustomEndpoints.callEndpoint("", new JSONObject(), new CloudCodeListener() {
            @Override
            public void done(Object o, BmobException e) {

            }
        });
        return this;
    }


    public Uri getSound() {
        return mSound;
    }

    public Notify setSound(Uri sound) {
        mSound = sound;
        return this;
    }

    public void show() {
        if (getId() != null)
            mManager.notify(getId(), notification());
    }


    public Notification notification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        if (getTitle() != null)
            builder.setContentTitle(getTitle());
        if (getText() != null)
            builder.setContentText(getText());
        if (getSmallIcon() != null)
            builder.setSmallIcon(getSmallIcon());
        if (getLargeIcon() != null)
            builder.setLargeIcon(getLargeIcon());
        if (getSubText() != null)
            builder.setSubText(getSubText());
        if (getPendingIntent() != null)
            builder.setContentIntent(getPendingIntent());
        if (getAutoCancel() != null && getAutoCancel())
            builder.setAutoCancel(getAutoCancel());
        if (getSound() != null)
            builder.setSound(getSound());


        return builder.build();
    }


}
