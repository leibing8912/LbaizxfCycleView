package cn.jianke.lbaizxfcycleview.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import cn.jianke.lbaizxfcycleview.widget.CycleView.CycleViewListener;

/**
 * @className: CycleViewAdapter
 * @classDescription: 轮播适配器
 * @author: leibing
 * @createTime: 2016/09/20
 */
public class CycleViewAdapter extends PagerAdapter{
    // 轮播view集合
    private List<View> mViews;
    // 轮播控件监听
    private CycleViewListener cycleViewListener;
    // 数据源大小
    private int size;

    /**
     * 构造函数
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param mViews 轮播view集合
     * @param cycleViewListener 轮播控件监听
     * @param size 数据源大小
     * @return
     */
    public CycleViewAdapter(List<View> mViews, CycleViewListener cycleViewListener, int size){
        this.mViews = mViews;
        this.cycleViewListener = cycleViewListener;
        this.size = size;
    }

    @Override
    public int getCount() {
        return mViews != null? mViews.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        View v = mViews.get(position);
        if (cycleViewListener != null) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int vPosition = position;
                    // 处理极端情况，此情况出现在轮播最后一张图切换到第一张图，ViewPaper实现轮播原理决定的。
                    if (vPosition > size)
                        vPosition = vPosition % size;
                    vPosition --;
                    if (vPosition < 0)
                        vPosition = 0;
                    cycleViewListener.onItemClick(vPosition);
                }
            });
        }
        container.addView(v);
        return v;
    }
}
