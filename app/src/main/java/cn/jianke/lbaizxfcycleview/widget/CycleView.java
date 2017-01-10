package cn.jianke.lbaizxfcycleview.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import cn.jianke.lbaizxfcycleview.R;
import cn.jianke.lbaizxfcycleview.adapter.CycleViewAdapter;
import cn.jianke.lbaizxfcycleview.model.CycleModel;
import cn.jianke.lbaizxfcycleview.utils.ImageLoader;

/**
 * @className: CycleView
 * @classDescription: 轮播图（基于ViewPager）
 * @author: leibing
 * @createTime: 2016/09/20
 */
public class CycleView extends FrameLayout implements ViewPager.OnPageChangeListener{
    // 默认轮播时间
    private int delay = 6000;
    // 实现轮播图的ViewPager
    private ViewPager mViewPager;
    // 标题
    private TextView mTitle;
    // 指示器容器
    private LinearLayout mIndicatorLy;
    // 指示器小圆点集合
    private ImageView[] mIndicators;
    // 是否循环，默认为false
    private boolean isCycle = false;
    // 是否轮播，默认为false
    private boolean isWheel = false;
    // 是否有轮播功能
    private boolean isHasWheel = false;
    // 轮播当前位置
    private int mCurrentPosition = 0;
    // 指示器图片，被选择状态
    private int mIndicatorSelected = R.mipmap.btn_appraise_selected;
    // 指示器图片，未被选择状态
    private int mIndicatorUnselected = R.mipmap.btn_appraise_normal;
    // 需要轮播的View集合
    private List<View> mViews = new ArrayList<>();
    // 轮播监听
    private CycleViewListener cycleViewListener;
    // 数据源
    private List<CycleModel> mData;
    // 轮播适配器
    private CycleViewAdapter mAdapter;
    // 轮播view默认占位图
    private Drawable defaultImage = getResources().getDrawable(R.drawable.default_holder);
    // 自定义Handler，用于控制轮播 add by leibing 2016/11/19
    private Handler mHandler = new Handler();
    // 轮播执行器 add by leibing 2016/11/19
    private Runnable cycleRunnable = new Runnable() {
        @Override
        public void run() {
            if (isWheel && isHasWheel) {
                mCurrentPosition++;
                if (mViewPager != null)
                    mViewPager.setCurrentItem(mCurrentPosition, false);
            }
            if (mHandler != null)
                mHandler.postDelayed(cycleRunnable, delay);
        }
    };

    public CycleView(Context context) {
        this(context, null);
    }

    public CycleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化view
        initView(context);
    }

    /**
     * 初始化view
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param context 上下文
     * @return
     */
    private void initView(Context context) {
        // 指定布局
        View view = LayoutInflater.from(context).inflate(R.layout.widget_cycle_view, null);
        // findView
        mViewPager = (ViewPager) view.findViewById(R.id.vp_cycle);
        mTitle = (TextView) view.findViewById(R.id.tv_cycle_title);
        mIndicatorLy = (LinearLayout) view.findViewById(R.id.ly_cycle_indicator);
        // 添加view到轮播view
        this.addView(view);
    }

    /**
     * 指示器靠右显示
     * @author leibing
     * @createTime 2016/09/21
     * @lastModify 2016/09/21
     * @param paddingRight 指示器距右边内边距
     * @param paddingBottom 指示器距底部内边距
     * @return
     */
    public void setAlignParentRight(int paddingRight, int paddingBottom){
        if (mIndicatorLy == null)
            return;

        // 设置为靠右
        mIndicatorLy.setGravity(Gravity.RIGHT);
        // 设置内边距
        mIndicatorLy.setPadding(0,0,paddingRight,paddingBottom);
        // 重新布局
        mIndicatorLy.requestLayout();
    }

    /**
     * 指示器靠右显示
     * @author leibing
     * @createTime 2016/09/21
     * @lastModify 2016/09/21
     * @param paddingLeft 指示器距左边内边距
     * @param paddingBottom 指示器距底部内边距
     * @return
     */
    public void setAlignParentLeft(int paddingLeft, int paddingBottom){
        // 设置为靠左
        mIndicatorLy.setGravity(Gravity.LEFT);
        // 设置内边距
        mIndicatorLy.setPadding(paddingLeft, 0, 0, paddingBottom);
        // 重新布局
        mIndicatorLy.requestLayout();
    }

    /**
     * 指示器设置居中显示
     * @author leibing
     * @createTime 2016/09/21
     * @lastModify 2016/09/21
     * @param paddingBottom 指示器距底部内边距
     * @return
     */
    public void setAlignParentCenter(int paddingBottom){
        if (mIndicatorLy == null)
            return;
        // 设置为居中
        mIndicatorLy.setGravity(Gravity.CENTER);
        // 设置内边距
        mIndicatorLy.setPadding(0, 0, 0, paddingBottom);
        // 重新布局
        mIndicatorLy.requestLayout();
    }

    /**
     * 设置数据源
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param mData 数据源
     * @param context 上下文
     * @param listener 轮播监听
     * @return
     */
    public void setData(List<CycleModel> mData , Context context,CycleViewListener listener){
        setData(mData, 0, context, defaultImage, listener);
    }

    /**
     * 设置数据源
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param mData 数据源
     * @param defaultImage 默认占位图（图片未加载出来前）
     * @param context 上下文
     * @param listener 轮播监听
     * @return
     */
    public void setData(List<CycleModel> mData ,
                        Drawable defaultImage,Context context, CycleViewListener listener){
        setData(mData, 0, context, defaultImage, listener);
    }

    /**
     * 设置数据源
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param mData 轮播view数据源
     * @param defaultPosition 默认显示位置
     * @param context 上下文
     * @param defaultImage 默认占位图（图片未加载出来前）
     * @param listener 轮播监听
     * @return
     */
    public void setData(List<CycleModel> mData, int defaultPosition,Context context,
                        Drawable defaultImage, CycleViewListener listener){
        // 若上下文为空则返回
        if (context == null)
            return;
        // 设置轮播view数据源
        this.mData = mData;
        // 如果数据源不存在或者其大小为0则设置当前布局为不可见
        if (mData == null || mData.size() == 0){
            this.setVisibility(View.GONE);
            return;
        }
        int size = mData.size();
        // 如果默认显示位置超过轮播view数目则默认位置从第一个位置开始
        if (defaultPosition >= size)
            defaultPosition = 0;
        // 轮播view数目为1，则不需要循环
        if (size == 1)
            isCycle = false;
        // 清除mViews数据
        mViews.clear();
        // 添加轮播view
        if (isCycle) {
            // 添加轮播图View，数量为集合数+2
            // 将最后一个View添加进来
            mViews.add(getCycleView(context, mData.get(size - 1).getUrl(), defaultImage));
            for (int i = 0; i < size; i++) {
                mViews.add(getCycleView(context, mData.get(i).getUrl(), defaultImage));
            }
            // 将第一个View添加进来
            mViews.add(getCycleView(context , mData.get(0).getUrl(), defaultImage));
        } else {
            // 只添加对应数量的View
            for (int i = 0; i < size; i++) {
                mViews.add(getCycleView(context, mData.get(i).getUrl(), defaultImage));
            }
        }
        // 设置轮播监听
        cycleViewListener = listener;
        // 初始化指示器
        initIndicators(size, getContext());
        // 设置指示器
        setIndicator(defaultPosition);
        // 设置适配器
        setAdapter(mViews, cycleViewListener, size);
        // 开始轮播
        startWheel(size);
    }

    /**
     * 设置适配器
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param mViews 轮播view集合
     * @param cycleViewListener 轮播监听
     * @param size 数据源大小
     * @return
     */
    private void setAdapter(List<View> mViews, CycleViewListener cycleViewListener, int size) {
        mAdapter = new CycleViewAdapter(mViews, cycleViewListener, size);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setAdapter(mAdapter);
        // 如果循环，则从第二个位置开始轮播,否者停留在第一个位置
        if (isCycle){
            mViewPager.setCurrentItem(1, false);
        }else {
            mViewPager.setCurrentItem(0, false);
        }
    }

    /**
     * 初始化指示器
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param size 数据源大小
     * @param context 上下文
     * @return
     */
    private void initIndicators(int size, Context context) {
        // 清除指示器容器
        mIndicatorLy.removeAllViews();
        // 当轮播view数目小于2则不需要指示器
        if (size < 2)
            return;
        // 初始化指示器图片集合
        mIndicators = new ImageView[size];
        for (int i = 0; i < mIndicators.length; i++) {
            mIndicators[i] = new ImageView(context);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 10, 0);
            mIndicators[i].setLayoutParams(lp);
            mIndicatorLy.addView(mIndicators[i]);
        }
    }

    /**
     * 设置指示器
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param selectedPosition 默认指示器位置
     * @return
     */
    private void setIndicator(int selectedPosition) {
        if (mData == null || mData.size() == 0 || selectedPosition >= mData.size())
            return;
        // 设置标题
        if (mTitle != null)
            mTitle.setText(mData.get(selectedPosition).getTitle());
        try {
            for (int i = 0; i < mIndicators.length; i++) {
                mIndicators[i].setBackgroundResource(mIndicatorUnselected);
            }
            if (mIndicators.length > selectedPosition)
                mIndicators[selectedPosition].setBackgroundResource(mIndicatorSelected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置指示器图片
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param select   选中时的图片
     * @param unselect 未选中时的图片
     * @return
     */
    public void setIndicators(int select, int unselect) {
        mIndicatorSelected = select;
        mIndicatorUnselected = unselect;
    }

    /**
     * 得到轮播图的View
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param context 上下文
     * @param url 图片链接地址
     * @param defaultImage 默认占位图
     * @return
     */
    private View getCycleView(Context context, String url, Drawable defaultImage) {
        RelativeLayout mRelativeLayout = new RelativeLayout(context);
        // 添加一个ImageView到布局
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        // 加载图片资源
        ImageLoader.load(context,imageView, url, defaultImage, defaultImage, false);
        mRelativeLayout.addView(imageView);
        return mRelativeLayout;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        int max = mViews.size() - 1;
        mCurrentPosition = position;
        if (isCycle()) {
            if (position == 0) {
                // 滚动到mView的1个（界面上的最后一个），将mCurrentPosition设置为max - 1
                mCurrentPosition = max - 1;
            } else if (position == max) {
                // 滚动到mView的最后一个（界面上的第一个），将mCurrentPosition设置为1
                mCurrentPosition = 1;
            }
            position = mCurrentPosition - 1;
        }
        setIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 0 && isCycle()) { // viewPager滚动结束
            //跳转到第mCurrentPosition个页面（没有动画效果，实际效果页面上没变化）
            mViewPager.setCurrentItem(mCurrentPosition, false);
        }
    }

    /**
     * 设置是否有轮播功能
     * @author leibing
     * @createTime 2016/09/22
     * @lastModify 2016/09/22
     * @param isHasWheel 是否有轮播功能
     * @return
     */
    public void setIsHasWheel(boolean isHasWheel){
        this.isHasWheel = isHasWheel;
        // 如有轮播功能则一定需要循环
        if (isHasWheel) {
            isCycle = true;
            isWheel = true;
        }else {
            isWheel = false;
        }
    }

    /**
     * 是否循环，默认开启。必须在setData前调用
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param isCycle 是否循环
     * @return
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * 是否处于循环状态
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param
     * @return
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * 设置是否轮播，默认轮播,轮播一定是循环的
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param isWheel 是否循环
     * @return
     */
    private void setWheel(boolean isWheel) {
        // 默认为不轮播
        this.isWheel = false;
        // 如果循环以及有轮播功能才开启轮播
        if (isCycle() && isHasWheel) {
            this.isWheel = isWheel;
        }
    }

    /**
     * 开始轮播
     * @author leibing
     * @createTime 2016/09/21
     * @lastModify 2016/09/21
     * @param size 轮播view数目
     * @return
     */
    private void startWheel(int size){
        if (size < 2 || !isCycle()){
            // 取消轮播
            setWheel(false);
            return;
        }
        // 设置轮播
        setWheel(true);
        // 开始轮播
        if (mHandler == null)
            mHandler = new Handler();
        if (cycleRunnable != null){
            mHandler.post(cycleRunnable);
        }
    }

    /**
     * 取消轮播
     * @author leibing
     * @createTime 2016/09/22
     * @lastModify 2016/09/22
     * @param
     * @return
     */
    public void cancelWheel(){
        if (mHandler != null) {
            mHandler.removeCallbacks(cycleRunnable);
            mHandler = null;
        }
    }

    /**
     * 是否处于轮播状态
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param
     * @return
     */
    private boolean isWheel() {
        return isWheel;
    }

    /**
     * 设置轮播暂停时间,单位毫秒（默认4000毫秒）
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param delay
     * @return
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                // 手指按下或者滑动的过程中停止轮播
                setWheel(false);
                cancelWheel();
                break;
            case MotionEvent.ACTION_UP:
                // 手指离开屏幕开始轮播
                setWheel(true);
                if (mHandler == null)
                    mHandler = new Handler();
                if (cycleRunnable != null){
                    mHandler.postDelayed(cycleRunnable, delay);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @interfaceName: CycleViewListener
     * @interfaceDescription: 轮播监听
     * @author: leibing
     * @createTime: 2016/09/20
     */
    public interface CycleViewListener{
        // 点击事件
        void onItemClick(int position);
    }
}
