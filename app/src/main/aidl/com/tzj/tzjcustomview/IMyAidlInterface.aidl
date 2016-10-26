// IMyAidlInterface.aidl
package com.tzj.tzjcustomview;

// Declare any non-default types here with import statements
import com.tzj.tzjcustomview.Book;
import com.tzj.tzjcustomview.IOnNewBookArrivedListener;

interface IMyAidlInterface {

    void add(int a,int b);

    //添加新书
    void addBook(in Book book);

    //获取图书列表
    List<Book> getBookList();

    //注册监听
    void registerListener(IOnNewBookArrivedListener listener);

    //取消监听
    void unRegisterListener(IOnNewBookArrivedListener listener);

}
