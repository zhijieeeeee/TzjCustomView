package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tzj.tzjcustomview.exchangeview.TestFragment;
import com.tzj.tzjcustomview.exchangeview.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> FileName： ExchangeActivity</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-01 9:04
 */
public class ExchangeActivity extends AppCompatActivity {

    private ViewPager vp;

    private List<TextView> viewList;

    private List<TestFragment> testFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        vp = (ViewPager) findViewById(R.id.vp);

        testFragmentList=new ArrayList<>();
        for(int i=0;i<4;i++){
            TestFragment testFragment=new TestFragment();
            testFragmentList.add(testFragment);
        }
        VpFragmentAdapter vpFragmentAdapter=new VpFragmentAdapter(getSupportFragmentManager());
        vp.setAdapter(vpFragmentAdapter);
        vp.setPageTransformer(true, new ZoomOutPageTransformer());
        vp.setOffscreenPageLimit(3);




//        viewList = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            TextView textView = new TextView(this);
//            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(200, 200);
//            textView.setLayoutParams(layoutParams);
//            textView.setText(i + "");
//            switch (i) {
//                case 0:
//                    textView.setBackgroundColor(Color.RED);
//                    break;
//                case 1:
//                    textView.setBackgroundColor(Color.BLUE);
//                    break;
//                case 2:
//                    textView.setBackgroundColor(Color.YELLOW);
//                    break;
//                case 3:
//                    textView.setBackgroundColor(Color.GREEN);
//                    break;
//            }
//            viewList.add(textView);
//        }
//        VpAdapter vpAdapter = new VpAdapter();
//        vp.setAdapter(vpAdapter);
//        vp.setPageTransformer(true, new ZoomOutPageTransformer());
//        vp.setOffscreenPageLimit(3);
    }

    class VpFragmentAdapter extends FragmentPagerAdapter {

        public VpFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return testFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return testFragmentList.size();
        }

    }

    class VpAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));

            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }
}
