package com.tzj.tzjcustomview.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.tzj.tzjcustomview.R;
import com.tzj.tzjcustomview.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class PuzzleActivity extends AppCompatActivity {

    private GridView gv;
    private PuzzleAdapter puzzleAdapter;
    //拼图实体列表
    private List<ItemBean> itemBeanList = new ArrayList<>();

    /**
     * 模式
     */
    private int mode = 3;

    //原图bitmap
    private Bitmap srcBitmap;

    //最后一张拼图
    private ItemBean lastItemBean;
    //最后一张拼图的bitmap
    private Bitmap lastBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        gv = (GridView) findViewById(R.id.gv);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) gv.getLayoutParams();
        lp.width = ScreenUtil.getScreenWidth(this);
        lp.height = ScreenUtil.getScreenWidth(this);
        gv.setLayoutParams(lp);
        gv.setNumColumns(mode);

        //初始化图片bitmap
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic4);
        //重新设置图片大小
        srcBitmap = resizeBitmap(ScreenUtil.getScreenWidth(this), ScreenUtil.getScreenWidth(this), srcBitmap);

        //平分图片生成列表
        createPuzzleItem(mode, srcBitmap);
        //把列表打乱成随机的
        getPuzzleGenerator();

        puzzleAdapter = new PuzzleAdapter(this, itemBeanList, mode);
        gv.setAdapter(puzzleAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isMoveable(position)) {
                    swapItems(itemBeanList.get(position), lastItemBean);
                    puzzleAdapter.notifyDataSetChanged();
                    if (isSuccess()) {
                        //最后一张自动填充
                        lastItemBean.setBitmap(lastBitmap);
                        puzzleAdapter.notifyDataSetChanged();
                        gv.setEnabled(false);
                    }
                }
            }
        });
    }

    /**
     * 根据选择的模式平分图片
     *
     * @param mode
     * @param srcBitmap
     */
    private void createPuzzleItem(int mode, Bitmap srcBitmap) {
        Bitmap bitmap;
        int itemWidth = srcBitmap.getWidth() / mode;
        int itemHeight = srcBitmap.getHeight() / mode;

        List<Bitmap> bitmapList = new ArrayList<>();

        for (int row = 1; row <= mode; row++) {
            for (int column = 1; column <= mode; column++) {
                //获取平分后的每块bitmap
                bitmap = Bitmap.createBitmap(
                        srcBitmap,
                        (column - 1) * itemWidth,
                        (row - 1) * itemHeight,
                        itemWidth,
                        itemHeight);
                bitmapList.add(bitmap);
                ItemBean itemBean = new ItemBean(
                        (row - 1) * mode + column,
                        (row - 1) * mode + column,
                        bitmap);
                itemBeanList.add(itemBean);
            }
        }
        //保存最后一张拼图，在拼图完成时填充
        lastBitmap = bitmapList.get(mode * mode - 1);
        //设置最后一张为空item
        bitmapList.remove(mode * mode - 1);
        itemBeanList.remove(mode * mode - 1);
        //空白拼图
//        Bitmap blankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic5);
//        //设置为跟图片的拼图项一样宽高
//        blankBitmap = Bitmap.createBitmap(blankBitmap, 0, 0, itemWidth, itemHeight);
        Bitmap blankBitmap = Bitmap.createBitmap(itemWidth, itemHeight, Bitmap.Config.ARGB_8888);
        //添加空白拼图到列表中
        bitmapList.add(blankBitmap);
        itemBeanList.add(new ItemBean(mode * mode, 0, blankBitmap));
        lastItemBean = itemBeanList.get(mode * mode - 1);
    }

    /**
     * 根据拼图区域的大小缩放图片
     *
     * @param newW
     * @param newH
     * @param srcBitmap
     * @return
     */
    private Bitmap resizeBitmap(float newW, float newH, Bitmap srcBitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(newW / srcBitmap.getWidth(), newH / srcBitmap.getHeight());
        Bitmap newBitmap = Bitmap.createBitmap(
                srcBitmap,
                0,
                0,
                srcBitmap.getWidth(),
                srcBitmap.getHeight(),
                matrix, true);
        return newBitmap;
    }

    /**
     * 生成随机的Item，打乱顺序
     */
    private void getPuzzleGenerator() {
        int index;
        //随机打乱顺序
        for (int i = 0; i < itemBeanList.size(); i++) {
            index = (int) (Math.random() * mode * mode);
            swapItems(itemBeanList.get(index), lastItemBean);
        }
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < itemBeanList.size(); i++) {
            data.add(itemBeanList.get(i).getItemId());
        }
        //判断是否有解
        if (canSolve(data)) {
            return;
        } else {
            //没有解，再次打乱
            getPuzzleGenerator();
        }
    }


    /**
     * 交换 点击item与空白item的位置
     * (只交换了bitmap和bitmapId,itemId不变，
     * 最后通过对比每个bean的bitmapId和itemId是否都相同来判断是否拼图完成)
     *
     * @param from  点击item
     * @param blank 空白item
     */
    private void swapItems(ItemBean from, ItemBean blank) {
        ItemBean tempItemBean = new ItemBean();
        //交换bitmapId
        tempItemBean.setBitmapId(from.getBitmapId());
        from.setBitmapId(blank.getBitmapId());
        blank.setBitmapId(tempItemBean.getBitmapId());
        //交换bitmap
        tempItemBean.setBitmap(from.getBitmap());
        from.setBitmap(blank.getBitmap());
        blank.setBitmap(tempItemBean.getBitmap());

        //设置新的blank;
        lastItemBean = from;
    }

    /**
     * 判断该数据是否有解
     *
     * @param data
     * @return
     */
    private boolean canSolve(List<Integer> data) {
        int blankId = lastItemBean.getItemId();
        if (data.size() % 2 == 1) {
            return PuzzleUtil.getInversions(data) % 2 == 0;
        } else {
            if (((blankId - 1) / mode) % 2 == 1) {
                return PuzzleUtil.getInversions(data) % 2 == 0;
            } else {
                return PuzzleUtil.getInversions(data) % 2 == 1;
            }
        }
    }

    /**
     * 判断点击的item是否可以移动
     *
     * @param position
     * @return
     */
    private boolean isMoveable(int position) {
        int blankId = lastItemBean.getItemId() - 1;
        //不同行相差为mode
        if (Math.abs(blankId - position) == mode) {
            return true;
        }
        //相同行，相差为1
        if (blankId / mode == position / mode
                && Math.abs(blankId - position) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断拼图是否成功
     *
     * @return
     */
    private boolean isSuccess() {
        for (ItemBean itemBean : itemBeanList) {
            //不是空白图
            if (itemBean.getBitmapId() != 0 &&
                    (itemBean.getItemId() == itemBean.getBitmapId())) {
                //比较成功，进行下一张
                continue;
                //空白图
            } else if (itemBean.getBitmapId() == 0 &&
                    itemBean.getItemId() == mode * mode) {
                //比较成功，进行下一张
                continue;
            } else {//比较失败
                return false;
            }
        }
        return true;
    }
}
