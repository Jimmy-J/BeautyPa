package com.example.jiaomin.beautypa.model;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/6.
 * 类描述：美拍的列表数据
 */

public class VideoEntity {
    /**
     * id : 600037103
     * url : http://www.meipai.com/media/600037103
     * cover_pic : http://mvimg2.meitudata.com/5809dac6c8c3e595.jpg!thumb320
     * screen_name : 万宝路路~
     * caption : 此景~此生必来一次
     * avatar : http://mvavatar1.meitudata.com/57f3c93a86a799616.jpg
     * plays_count : 1885
     * comments_count : 13
     * likes_count : 51
     */

    private int id;
    private String url;
    private String cover_pic;
    private String screen_name;
    private String caption;
    private String avatar;
    private int plays_count;
    private int comments_count;
    private int likes_count;


    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getCaption() {
        return caption;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getPlays_count() {
        return plays_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public int getLikes_count() {
        return likes_count;
    }
}
