package cn.jianke.lbaizxfcycleview.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import cn.jianke.lbaizxfcycleview.R;
import cn.jianke.lbaizxfcycleview.model.CycleModel;
import cn.jianke.lbaizxfcycleview.widget.CycleView;

/**
 * @className: MainActivity
 * @classDescription: 图片轮播测试首页
 * @author: leibing
 * @createTime: 2016/09/21
 */
public class MainActivity extends AppCompatActivity {
    // 数据源
    private List<CycleModel> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // findview
        final CycleView adsCycleView = (CycleView) findViewById(R.id.cycleview_ads);
        // 初始化数据源
        mData = new ArrayList<>();
        // 添加数据源
        CycleModel cycleModel = new CycleModel("1",
                "http://g.hiphotos.baidu.com/imgad/pic/item/f603918fa0ec08fa9f0b7dd85eee3d6d55fbda42.jpg");
        mData.add(cycleModel);
        cycleModel = new CycleModel("2",
                "http://g.hiphotos.baidu.com/imgad/pic/item/4a36acaf2edda3cc6a22d65f06e93901203f928e.jpg");
        mData.add(cycleModel);
        cycleModel = new CycleModel("3",
                "http://g.hiphotos.baidu.com/imgad/pic/item/023b5bb5c9ea15cec0e68e76b1003af33a87b241.jpg");
        mData.add(cycleModel);
        cycleModel = new CycleModel("4",
                "http://f.hiphotos.baidu.com/imgad/pic/item/5366d0160924ab18ead18f4832fae6cd7a890b8d.jpg");
        mData.add(cycleModel);
        // 设置显示方式（居中,内边距离下24dp）
        adsCycleView.setAlignParentCenter(24);
        // 设置为有轮播功能
        adsCycleView.setIsHasWheel(true);
        // 设置数据源并设置监听
        adsCycleView.setData(mData, MainActivity.this, new CycleView.CycleViewListener() {
            @Override
            public void onItemClick(int position) {
                position = position + 1;
                Toast.makeText(MainActivity.this, "这是第" + position + "个图",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
