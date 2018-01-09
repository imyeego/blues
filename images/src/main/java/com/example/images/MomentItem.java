package com.example.images;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class MomentItem {


    private String avatar = null;
    private String name = null;
    private String content = null;
    private List<String> images = null;


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
    public List<String> getImages() {
        return images;
    }
    public void setImages(List<String> images) {
        this.images = images;
    }

}
