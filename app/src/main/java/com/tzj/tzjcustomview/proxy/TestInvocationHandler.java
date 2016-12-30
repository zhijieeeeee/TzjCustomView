package com.tzj.tzjcustomview.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class TestInvocationHandler implements InvocationHandler {

    private Object object;

    public TestInvocationHandler(Object object) {
        this.object = object;
    }

    //每个代理类调用里面的方法时，都会调用这个方法，这里可以做些公共的操作
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i("MyLog", "method.getName()=" + method.getName());
        //这里反射调用委托类中的方法
        Object ob = method.invoke(object, args);
        Log.i("MyLog", "tzj");
        return ob;
    }
}
