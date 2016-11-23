package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class TabVpActivity extends AppCompatActivity {

    private ViewPager vp;
    private TabLayout tabLayout;

    private String[] title = new String[]{"Page1", "Page2", "Page3",
            "Page4"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_vp);

        vp = (ViewPager) findViewById(R.id.vp);
        tabLayout = (TabLayout) findViewById(R.id.tab);

        VpAdapter adapter = new VpAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(10);
        tabLayout.setupWithViewPager(vp);

        //自定义的tab
        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.tab_view);
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(android.R.id.text1).setSelected(true);
            }
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 将离开的tab的textView的select属性设置为true
                tab.getCustomView().findViewById(android.R.id.text1).setSelected(true);
                // 将viewpager的item与 tablayout的同步
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 将离开的tab的textView的select属性设置为false
                tab.getCustomView().findViewById(android.R.id.text1).setSelected(false);
                // 将viewpager的item与 tablayout的同步
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //计算tab宽度，设置模式
//        dynamicSetTabLayoutMode(tabLayout);
    }

    class VpAdapter extends FragmentPagerAdapter {

        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            return VpFragment.getInstance();
        }

        @Override
        public int getCount() {
            return title.length;
        }
    }


    public void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getScreenWith();

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

    public int getScreenWith() {
        return getResources().getDisplayMetrics().widthPixels;
    }
}
