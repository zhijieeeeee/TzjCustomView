// IMyAidlInterface.aidl
package com.tzj.tzjcustomview;

// Declare any non-default types here with import statements
import com.tzj.tzjcustomview.Book;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void add(int a,int b);

    void addBook(in Book book);

}
