package com.swu.module_order.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swu.module_order.adapter.ConfirmOrderGoodsAdapter;

/**
 * Created by 刘金豪 on 2021/1/12
 * desc: 商品Item的decoration，主要用来绘制分割线
 */
public class GoodItemDecoration extends RecyclerView.ItemDecoration {
    private int groupHeaderHeight;
    private Paint linePaint;

    public GoodItemDecoration(){
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
    }
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();//屏幕中itemView的个数
        //分割线的左右边界
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0;i < childCount;i++){
            View item = parent.getChildAt(i);//得到每一个item
            if(parent.getChildLayoutPosition(item) != 0){
                //position为第一个的item 不用绘制
                c.drawRect(left,item.getTop() - 1,right,item.getTop(),linePaint);
            }
        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getAdapter() instanceof ConfirmOrderGoodsAdapter){
            ConfirmOrderGoodsAdapter adapter = (ConfirmOrderGoodsAdapter) parent.getAdapter();
            //得到position
            //int position = parent.getChildLayoutPosition(view);
            outRect.set(0,1,0,0);//第一个item上面不留空隙
        }
    }
}
