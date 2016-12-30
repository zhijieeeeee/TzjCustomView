package com.tzj.tzjcustomview.proxy;

import android.util.Log;

/**
 * <p>
 * Description：委托类
 * </p>
 *
 * @author tangzhijie
 */
public class OperateImpl implements Operate {
    @Override
    public void op1() {
        Log.i("MyLog", "op1");
    }

    @Override
    public void op2() {
        Log.i("MyLog", "op2");
    }

    @Override
    public void op3() {
        Log.i("MyLog", "op3");
    }
}
