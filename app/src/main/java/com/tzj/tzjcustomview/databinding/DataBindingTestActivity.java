package com.tzj.tzjcustomview.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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
        binding.setAge("24");
        binding.setShow(true);
    }
}
