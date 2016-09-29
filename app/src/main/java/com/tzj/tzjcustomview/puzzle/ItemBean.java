package com.tzj.tzjcustomview.puzzle;

import android.graphics.Bitmap;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class ItemBean {

    private int itemId;
    private int bitmapId;
    private Bitmap bitmap;

    public ItemBean() {

    }

    public ItemBean(int itemId, int bitmapId, Bitmap bitmap) {
        this.itemId = itemId;
        this.bitmapId = bitmapId;
        this.bitmap = bitmap;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getBitmapId() {
        return bitmapId;
    }

    public void setBitmapId(int bitmapId) {
        this.bitmapId = bitmapId;
    }
}
