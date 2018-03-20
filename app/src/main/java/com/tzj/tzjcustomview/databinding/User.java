package com.tzj.tzjcustomview.databinding;

import android.databinding.ObservableField;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class User {

    private String id;
    private String name;
    private int number;
    private String display;
    private boolean show;
    //可及时修改UI的
    public final ObservableField<String> sex = new ObservableField<>("男");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
