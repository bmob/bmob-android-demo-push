package cn.bmob.push.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.push.R;
import cn.bmob.push.fragment.ChannelFragment;
import cn.bmob.push.fragment.IntegrationFragment;
import cn.bmob.push.fragment.PushFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    private Fragment currentFragment = null;
    private IntegrationFragment mIntegrationFragment = null;
    private ChannelFragment mChannelFragment = null;
    private PushFragment mPushFragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_integration:
                    switchContent(currentFragment, mIntegrationFragment);
                    return true;
                case R.id.navigation_channel:
                    switchContent(currentFragment, mChannelFragment);
                    return true;
                case R.id.navigation_push:
                    switchContent(currentFragment, mPushFragment);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mIntegrationFragment = new IntegrationFragment();
        mChannelFragment = new ChannelFragment();
        mPushFragment = new PushFragment();
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigation.setSelectedItemId(R.id.navigation_integration);

    }


    /**
     * 切换模块
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment == null) {
            transaction.add(R.id.content, to).commit();
            currentFragment = to;
            return;
        }
        if (currentFragment != to) {
            currentFragment = to;
            if (!to.isAdded()) {
                if (from.isAdded()) {
                    transaction.hide(from).add(R.id.content, to).commit();
                } else {
                    transaction.add(R.id.content, to).commit();
                }
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }
}
