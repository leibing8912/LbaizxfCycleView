package cn.jianke.lbaizxfcycleview.model;

/**
 * @className: CycleModel
 * @classDescription: 轮播数据
 * @author: leibing
 * @createTime:2016/09/20
 */
public class CycleModel {
    // 链接地址
    private String url;
    // 标题
    private String title;

    /**
     *
     * @author leibing
     * @createTime 2016/09/20
     * @lastModify 2016/09/20
     * @param title 标题
     * @param url 链接地址
     * @return
     */
    public CycleModel(String title, String url) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
