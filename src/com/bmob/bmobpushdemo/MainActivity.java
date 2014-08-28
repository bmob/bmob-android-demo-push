package com.bmob.bmobpushdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;

public class MainActivity extends Activity implements OnClickListener{
	
	private static String APPID = "";
	BmobPushManager bmobPushManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Toast.makeText(this, "请记得将你的AppId 填写在MainActivity类中的APPID变量值上", Toast.LENGTH_SHORT).show();
		
		// 初始化BmobSDK
		Bmob.initialize(this, APPID);
		// 使用推送服务时的初始化操作
	    BmobInstallation.getCurrentInstallation(this).save();
	    
	    // 启动推送服务
	    BmobPush.startWork(this, APPID);
	    
	    // 创建推送消息的对象
	    bmobPushManager = new BmobPushManager(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_sendAll:
			// 推送一条消息给所有安装此应用的设备
			bmobPushManager.pushMessageAll("这是给所有设备推送的一条消息。");
			break;
		case R.id.btn_sendMsgToAndroid:
			// 创建Installation表的BmobQuery对象
			BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
			// 并添加条件为设备类型属于android
			query.addWhereEqualTo("deviceType", "android");
			// 设置推送条件给bmobPushManager对象。
			bmobPushManager.setQuery(query);
			// 设置推送消息，服务端会根据上面的查询条件，来进行推送这条消息
			bmobPushManager.pushMessage("这是一条推送给所有Android设备的消息。");
			break;

		default:
			break;
		}
	}
}
