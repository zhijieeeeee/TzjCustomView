package com.tzj.tzjcustomview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tzj.tzjcustomview.wheelviewtest.WheelView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class WheelViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheelview_dialog);

        WheelView wv = (WheelView)findViewById(R.id.wheelview);
        wv.setOffset(2);
        ArrayList<String> list = new ArrayList<>();
//        for (SupportBankModel supportBankModel : banks) {
//            list.add(supportBankModel.fb_bankname);
//        }
        list.add("公司刚");
        list.add("公司刚1");
        list.add("公司刚2");
        list.add("公司刚3");
        list.add("公司刚4");
        list.add("公司刚5");
        list.add("公司刚6");
        wv.setItems(list);
        wv.setSeletion(3);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {//wheelview选中银行回调
            @Override
            public void onSelected(int selectedIndex, String item) {
                String currentBankItem = item;
               int  selectBankIndex = selectedIndex - 2;

                Log.i("MyLog","currentBankItem="+currentBankItem);
                Log.i("MyLog","selectBankIndex="+selectBankIndex);
            }
        });
    }
}
