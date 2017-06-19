package com.example.king.gou.bean;

import java.util.List;

/**
 * Created by king on 2017/6/19.
 */

public class NoticeContent {
    /**
     * rc : rc
     * msg : msg
     * id : id
     * others : {"title":"title","content":"content","user":"user","time":"time","sticky":"sticky"}
     */

    private boolean rc;
    private String msg;
    private int id;
    private List<OthersBean> others;

    public boolean getRc() {
        return rc;
    }

    public void setRc(boolean rc) {
        this.rc = rc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OthersBean> getOthers() {
        return others;
    }

    public void setOthers(List<OthersBean> others) {
        this.others = others;
    }

    public static class OthersBean {
        /**
         * title : title
         * content : content
         * user : user
         * time : time
         * sticky : sticky
         */

        private String title;
        private String content;
        private String user;
        private String time;
        private int sticky;

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

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getSticky() {
            return sticky;
        }

        public void setSticky(int sticky) {
            this.sticky = sticky;
        }
    }
    /*{rc=false,
     msg=,
      id=0.0,
      others=
      {title=银天下震撼上线,
       content=<p>我们始终保持低调谦逊的态度接受所有人的批评与建议，也衷心欢迎大家给予我们更多的意见和支持！</p>
                                                         ,
                                                          user=jingang123,
     /*                                                      time=2016-09-05 21:59:36, sticky=1.0}}*//*
    boolean rc;
    String msg;
    int id;

    List<others> otherses;

    public List<others> getOtherses() {
        return otherses;
    }

    public void setOtherses(List<others> otherses) {
        this.otherses = otherses;
    }

    public boolean isRc() {
        return rc;
    }

    public void setRc(boolean rc) {
        this.rc = rc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public class others {
        String title;
        String content;
        String user;
        String time;
        int sticky;

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

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getSticky() {
            return sticky;
        }

        public void setSticky(int sticky) {
            this.sticky = sticky;
        }
    }*/

}
