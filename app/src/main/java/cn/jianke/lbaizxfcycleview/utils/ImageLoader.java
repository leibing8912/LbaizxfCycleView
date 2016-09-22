package cn.jianke.lbaizxfcycleview.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @className: ImageLoader
 * @classDescription: 图片封装
 * @author: leibing
 * @createTime: 2016/09/20
 */
public class ImageLoader {

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param url 图片链接
     * @param defaultImage 默认占位图片
     * @return
     */
    public static void load(Context context, ImageView imageView, String url, Drawable defaultImage){
        load(context, imageView, url, defaultImage, null, false);
    }

    /**
     * 图片加载
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     * @param context 上下文
     * @param imageView 图片显示控件
     * @param url 图片链接
     * @param defaultImage 默认占位图片
     * @param errorImage 加载失败后图片
     * @param  isCropCircle 是否圆角
     * @return
     */
    public static void load(Context context, ImageView imageView, String url, Drawable defaultImage,
                     Drawable errorImage , boolean isCropCircle){
        // 图片加载库采用Glide框架
        DrawableTypeRequest request = Glide.with(context).load(url);
        // 设置scaleType
        request.centerCrop();
        // 圆角裁切
        if (isCropCircle)
            request.bitmapTransform(new CropCircleTransformation(context));
                request.placeholder(defaultImage) //设置资源加载过程中的占位Drawable
                .crossFade() //设置加载渐变动画
                .priority(Priority.NORMAL) //指定加载的优先级，优先级越高越优先加载，
                // 但不保证所有图片都按序加载
                // 枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW
                // 默认为Priority.NORMAL
                .fallback(null) //设置model为空时要显示的Drawable
                // 如果没设置fallback，model为空时将显示error的Drawable，
                // 如果error的Drawable也没设置，就显示placeholder的Drawable
                .error(errorImage) //设置load失败时显示的Drawable
                .skipMemoryCache(true) //设置跳过内存缓存，但不保证一定不被缓存
                // （比如请求已经在加载资源且没设置跳过内存缓存，这个资源就会被缓存在内存中）
                .diskCacheStrategy(DiskCacheStrategy.RESULT) //缓存策略DiskCacheStrategy.SOURCE：
                // 缓存原始数据，DiskCacheStrategy.RESULT：
                // 缓存变换(如缩放、裁剪等)后的资源数据，
                // DiskCacheStrategy.NONE：什么都不缓存，
                // DiskCacheStrategy.ALL：缓存SOURC和RESULT。
                // 默认采用DiskCacheStrategy.RESULT策略，
                // 对于download only操作要使用DiskCacheStrategy.SOURCE
                .into(imageView);
    }
}
