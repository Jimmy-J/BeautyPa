package com.example.jiaomin.beautypa.model;

import java.util.List;

/**
 * Created by MrJiaoMin@outlook.com on 2016/12/6.
 * 类描述：
 */

public class NewsDetailsEntity {
    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private String type;
    private String id;
    private String[] js;
    private String[] css;
    private List<RecommenderEntity> recommenderEntities;
    private SectionEntity section;

    public String getBody() {
        return body;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShare_url() {
        return share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String[] getJs() {
        return js;
    }

    public String[] getCss() {
        return css;
    }

    public List<RecommenderEntity> getRecommenderEntities() {
        return recommenderEntities;
    }

    public SectionEntity getSection() {
        return section;
    }
}
