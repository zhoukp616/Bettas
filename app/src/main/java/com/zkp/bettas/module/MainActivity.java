package com.zkp.bettas.module;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.zkp.bettas.R;
import com.zkp.bettas.base.BaseFragment;
import com.zkp.bettas.module.home.HomeFragment;
import com.zkp.bettas.utils.BottomNavigationViewHelper;
import com.zkp.bettas.utils.ToastUtil;

import java.util.ArrayList;

import static android.view.KeyEvent.KEYCODE_BACK;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;

    private String[] tags = new String[]{"home", "live", "video", "notice", "me"};
    /**
     * 选中的位置
     */
    private int position;
    /**
     * 页面的集合
     */
    private ArrayList<BaseFragment> baseFragments;
    /**
     * 记录选中的fragment
     */
    private BaseFragment content;
    /**
     * 是否要退出
     */
    private boolean isExit = false;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    position = 0;
                    switchContent(content, baseFragments.get(position), position);
                    return true;
                case R.id.navigation_live:
                    position = 1;
                    switchContent(content, baseFragments.get(position), position);
                    return true;
                case R.id.navigation_video:
                    position = 2;
                    switchContent(content, baseFragments.get(position), position);
                    return true;
                case R.id.navigation_notice:
                    position = 3;
                    switchContent(content, baseFragments.get(position), position);
                    return true;
                case R.id.navigation_me:
                    position = 4;
                    switchContent(content, baseFragments.get(position), position);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        baseFragments = new ArrayList<>();
        if (savedInstanceState != null) {
            baseFragments.add((HomeFragment) manager.findFragmentByTag(tags[0]));
            baseFragments.add((HomeFragment) manager.findFragmentByTag(tags[1]));
            baseFragments.add((HomeFragment) manager.findFragmentByTag(tags[2]));
            baseFragments.add((HomeFragment) manager.findFragmentByTag(tags[3]));
            baseFragments.add((HomeFragment) manager.findFragmentByTag(tags[4]));
        } else {
            //首页
            baseFragments.add(new HomeFragment());
            baseFragments.add(new HomeFragment());
            baseFragments.add(new HomeFragment());
            baseFragments.add(new HomeFragment());
            baseFragments.add(new HomeFragment());
        }
        position = 0;
        content = baseFragments.get(0);
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.frameLayout, content).commit();
    }

    private void initView() {
        navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * 再按一次退出应用
     *
     * @param keyCode keyCode
     * @param event   event
     * @return boolean
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastUtil.showToast(getApplicationContext(), "再按一次退出应用");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * fragment 切换
     *
     * @param from 当前页面
     * @param to   要显示的页面
     */
    public void switchContent(BaseFragment from, BaseFragment to, int position) {
        if (content != to) {
            content = to;
            //1.得到FragmentManger
            FragmentManager manager = getSupportFragmentManager();
            //2.开启事务
            FragmentTransaction ft = manager.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                // 隐藏当前的fragment，add下一个到Activity中
                ft.hide(from).add(R.id.frameLayout, to, tags[position]).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                ft.hide(from).show(to).commit();
            }
        }
    }

}
