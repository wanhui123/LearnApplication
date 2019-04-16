package com.wh.learnapplication.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 从LitePal 2.0.0版本开始建议使用继承LitePalSupport类，
 * DataSupport类已经被标为了废弃，虽然暂时还可以正常工作，但是不建议再继续使用了
 */
public class Movie extends LitePalSupport {
    //运用注解来为字段添加index标签

    //name是唯一的，且默认值为unknown
    @Column(unique = true, defaultValue = "unknown")
    private String name;

    //忽略即是不在数据库中创建该属性对应的字段
    @Column(ignore = true)
    private float price;

    private byte[] cover;
    private int duration;

    //不为空
    @Column(nullable = false)
    private String director;

    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
