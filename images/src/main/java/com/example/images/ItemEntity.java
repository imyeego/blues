package com.example.images;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class ItemEntity {

    private String avatar; // 用户头像URL
    private String name; // 名字
    private String content; // 内容
    private ArrayList<String> images; // 九宫格图片的URL集合


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
