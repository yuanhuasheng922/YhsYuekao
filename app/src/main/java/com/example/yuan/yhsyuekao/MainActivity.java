package com.example.yuan.yhsyuekao;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.yuan.yhsyuekao.adapter.FragemntAdapter;
import com.example.yuan.yhsyuekao.fragemnt.LeftFragemnt;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //判断为空是新建fragment
        if (savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout,new LeftFragemnt())
                    .commit();
        }
        //侧拉框
        //找控件
        drawerLayout = findViewById(R.id.drawer);
        //获取actionbar
        ActionBar actionBar = getSupportActionBar();
        //允许弹出侧拉框
        actionBar.setDisplayHomeAsUpEnabled(true);
        //得到中间人
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        //同步
        toggle.syncState();
            //抽屉监听
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Log.d("TAG","抽屉打开了");
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Log.d("TAG","抽屉关闭了");
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });


        //Fragemnt
        tabLayout = findViewById(R.id.table);
        viewPager = findViewById(R.id.viewpager);
            //适配器
        viewPager.setAdapter(new FragemntAdapter(getSupportFragmentManager()));
        //关联
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
