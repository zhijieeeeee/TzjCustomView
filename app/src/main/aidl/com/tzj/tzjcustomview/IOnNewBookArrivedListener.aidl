// IOnNewBookArrivedListener.aidl
package com.tzj.tzjcustomview;

// Declare any non-default types here with import statements
import com.tzj.tzjcustomview.Book;

//新书到来的监听
interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book book);
}
