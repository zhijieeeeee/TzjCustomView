package com.tzj.tzjcustomview.annotation;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class AnnotationUse {

    public static void use(Object o) {
        try {
            Class<?> clazz = o.getClass();
//            //获得指定的方法
//            Method method = clazz.getDeclaredMethod("annotationMethod");
//            //获得指定的注解
//            TestAnnotation testAnnotation = method.getAnnotation(TestAnnotation.class);
//            Log.i("MyLog", testAnnotation.name());
//            Log.i("MyLog", testAnnotation.age() + "");

            //获得类中的所有方法
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                TestAnnotation testAnnotation = method.getAnnotation(TestAnnotation.class);
                if (testAnnotation != null) {
                    Log.i("MyLog", testAnnotation.name());
                    Log.i("MyLog", testAnnotation.age() + "");
                }
            }

            //获取类的注解
            OnValueAnnotation onValueAnnotation=clazz.getAnnotation(OnValueAnnotation.class);
            if(onValueAnnotation!=null){
                Log.i("MyLog", onValueAnnotation.value() + "");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
