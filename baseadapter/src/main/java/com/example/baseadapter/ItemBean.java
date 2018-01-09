package com.example.baseadapter;

/**
 * Created by Administrator on 2017/9/12 0012.
 */

public class ItemBean {

    public int itemImageId;
    public String itemTitle;
    public String itemContent;

    public ItemBean(int itemImageId, String itemTitle, String itemContent) {
        this.itemImageId = itemImageId;
        this.itemTitle = itemTitle;
        this.itemContent = itemContent;
    }
}
