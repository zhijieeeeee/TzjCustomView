package com.tzj.tzjcustomview.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tzj.tzjcustomview.R;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class DataBindingTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_data_binding);

        User user = new User();
        user.setId("11111");
        user.setName("zhijieeeeee");
        user.setNumber(1);
        user.setShow(true);
        //ActivityDataBindingBinding 类是自动生成的，所有的 set 方法也是根据 variable 名称生成的。
        //这是绑定
        binding.setUser(user);

        binding.setAge(25);
//        binding.setShow(true);


        MyHandler myHandler = new MyHandler(this);
        binding.setHandler(myHandler);

        Fund fund = new Fund();
        fund.setFundId(1111);
        fund.setName("上证50指数");
        binding.setFund(fund);

        //获取设置了id的控件
        binding.tvId.getText();

        //显示图片,与DataBindingUtil结合
        binding.setImageUrl("http://cdn.duitang.com/uploads/item/201604/18/20160418022601_kfGHc.jpeg");
        binding.setImageUrl2("http://s10.sinaimg.cn/middle/4da45119hf686c55d24d9&690");
    }

    //点击事件，直接在xml中使用
    public void open(View view) {
        Toast.makeText(this, "哈哈哈哈", Toast.LENGTH_SHORT).show();
    }

    public void clickImg(View view) {
        Toast.makeText(this, "你点击了图片", Toast.LENGTH_SHORT).show();
    }
}
