package com.tzj.tzjcustomview.databinding;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by tangzhijie on 2018/3/20.
 */

public class MyHandler {

    private Context context;

    public MyHandler(Context context) {
        this.context = context;
    }

    public void onBtn1Click() {
        Toast.makeText(context, "onBtn1Click", Toast.LENGTH_SHORT).show();
    }

    public void onBtn2Click() {
        Toast.makeText(context, "onBtn2Click", Toast.LENGTH_SHORT).show();
    }

    //使用View，并传递数据
    public void onBtn3Click(View view, User user) {
        Toast.makeText(context, ((Button) view).getText().toString() + "onBtn3Click===" + user.getName(), Toast.LENGTH_SHORT).show();
    }

    public boolean onLongClick() {
        Toast.makeText(context, "onLongClick", Toast.LENGTH_SHORT).show();
        return true;
    }

    //使用ObservableField更新ui
    public void changeSex(User user) {
        if (user.sex.get().equals("男")) {
            user.sex.set("女");
        } else {
            user.sex.set("男");
        }
    }

    //使用继承BaseObservable更新ui
    public void changeFund(Fund fund) {
        fund.setFundId(2222);
        fund.setName("金鹰核心");
        Toast.makeText(context, "基金名称=" + fund.getName(), Toast.LENGTH_SHORT).show();
    }
}
