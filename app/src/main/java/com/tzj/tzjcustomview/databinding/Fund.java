package com.tzj.tzjcustomview.databinding;

import android.databinding.BaseObservable;

/**
 * Created by tangzhijie on 2018/3/21.
 */

public class Fund extends BaseObservable {

    private int fundId;
    private String name;

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
        notifyChange();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }
}
