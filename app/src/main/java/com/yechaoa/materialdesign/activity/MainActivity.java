package com.yechaoa.materialdesign.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.yechaoa.materialdesign.R;
import com.yechaoa.materialdesign.fragment.Fragment1;
import com.yechaoa.materialdesign.fragment.FragmentHome;
import com.yechaoa.materialdesign.fragment.FragmentMe;

import butterknife.BindView;

public class MainActivity extends ToolbarActivity {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setToolbar() {
        mToolbar.setTitle(R.string.tab_layout);
    }

    @Override
    protected void initView() {

        //设置返回键不显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //回调刷新toolbar的menu，页面初始化或者在需要的时候调用
        invalidateOptionsMenu();

        //跳转到登陆界面
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivityForResult(intent,1);

        //设置adapter
        final SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        //关联viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        //设置图标
        //Todo:修改layout底部的图标
        mTabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher); //第一个Tab
        mTabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher); //第二个Tab
        mTabLayout.getTabAt(2).setIcon(R.mipmap.ic_launcher); //第三个Tab
        //设置默认选中
        mTabLayout.getTabAt(2).select();
        //设置监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中
            }
        });
    }

    private class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private String tabTitles[] = new String[]{"Trans", "Home", "Me"};
        // Todo：设置对应的Fragment（修改名字）
        private Fragment[] mFragment = new Fragment[]{new Fragment1(), new FragmentHome(), new FragmentMe()};

        private SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragment[position];
        }

        @Override
        public int getCount() {
            return mFragment.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

    }

    //共享信息的函数，考虑重写
    //检验是否能够获取用户的userName信息
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            String userName = data.getStringExtra("userName");
            //if (!TextUtils.isEmpty(userName)){
            Toast.makeText(MainActivity.this,"登陆成功："+ userName, Toast.LENGTH_SHORT).show();

            if(userName==null){
                //跳转到登陆界面
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivityForResult(intent,1);
            }
        }
        mViewPager.getAdapter().notifyDataSetChanged();
    }

}
