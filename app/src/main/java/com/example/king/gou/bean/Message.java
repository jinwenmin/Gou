package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/8/8.
 */

public class Message {
    int chat_id;
    String title;
    String content;
    String sname;
    String uname;
    String chat_date;
    int readed;
    int is_alert;

    @Override
    public String toString() {
        return "Message{" +
                "chat_id=" + chat_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sname='" + sname + '\'' +
                ", uname='" + uname + '\'' +
                ", chat_date='" + chat_date + '\'' +
                ", readed=" + readed +
                ", is_alert=" + is_alert +
                '}';
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getChat_date() {
        return chat_date;
    }

    public void setChat_date(String chat_date) {
        this.chat_date = chat_date;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }

    public int getIs_alert() {
        return is_alert;
    }

    public void setIs_alert(int is_alert) {
        this.is_alert = is_alert;
    }
}
