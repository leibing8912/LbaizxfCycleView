# LbaizxfCycleView
一个基于Rxjava + ViewPaper实现的图片轮播器,能自定义指示器,能自定义是否轮播、是否循环等.

### 特性
* 支持指示器显示方式（靠左、居中、靠右）自定义
* 支持指示器图标自定义
* 支持设置图片默认占位图
* 支持设置是否轮播
* 支持设置是否循环
* 支持设置标题
* 支持设置轮播间隔时间
* 支持onClick监听事件


### Usage

##### 依赖
Maven:
```javascript
<dependency>
  <groupId>cn.jianke.lbaizxfcycleview</groupId>
  <artifactId>app</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
Gradle:
```javascript
compile 'cn.jianke.lbaizxfcycleview:app:1.0.1'
```

Ivy:
```javascript
<dependency org='cn.jianke.lbaizxfcycleview' name='app' rev='1.0.1'>
  <artifact name='$AID' ext='pom'></artifact>
</dependency>
```

##### code

```javascript
/**
 * @className: MainActivity
 * @classDescription: LbaizxfCycleView测试页
 * @author: leibing
 * @createTime: 2016/09/26
 */
public class MainActivity extends AppCompatActivity {
    // 显示轮播图片
    private CycleView lbaizxfCvw;
    // 数据源
    private List<CycleModel> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // findView
        lbaizxfCvw = (CycleView) findViewById(R.id.cvw_lbaizxf);
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
        lbaizxfCvw.setAlignParentCenter(24);
        // 设置为有轮播功能
        lbaizxfCvw.setIsHasWheel(true);
        // 设置数据源并设置监听
        lbaizxfCvw.setData(mData, new CycleView.CycleViewListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "这是第" + position + "个图",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

Demo地址:[LbaizxfCycleViewDemo](https://github.com/leibing8912/LbaizxfCycleViewDemo)

### Author
* QQ:872721111
* Email:leibing1989@126.com
* Github:[leibing@github](https://github.com/leibing8912)
* 简书:[leibing@jianshu](http://www.jianshu.com/users/e3057e46c9e9/latest_articles)
* 掘金:[leibing@juejin](http://gold.xitu.io/user/579eb39ea633bd006005ec92)

### License
Copyright 2016 leibing

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
