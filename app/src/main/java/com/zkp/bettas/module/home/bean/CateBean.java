package com.zkp.bettas.module.home.bean;

import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.bean
 * @time: 2018/8/15 10:57
 * @description:
 */
public class CateBean {

    /**
     * error : 0
     * data : [{"title":"手游","show_order":"1","identification":"3e760da75be261a588c74c4830632360","is_video":0,"is_show_cate1_icon":0},{"title":"娱乐","show_order":"2","identification":"9acf9c6f117a4c2d02de30294ec29da9","is_video":0,"is_show_cate1_icon":0},{"title":"游戏","show_order":"3","identification":"ba08216f13dd1742157412386eee1225","is_video":0,"is_show_cate1_icon":0},{"title":"趣玩","show_order":"4","identification":"393b245e8046605f6f881d415949494c","is_video":0,"is_show_cate1_icon":0}]
     */

    private int error;
    private List<DataBean> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 手游
         * show_order : 1
         * identification : 3e760da75be261a588c74c4830632360
         * is_video : 0
         * is_show_cate1_icon : 0
         */

        private String title;
        private String show_order;
        private String identification;
        private int is_video;
        private int is_show_cate1_icon;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShow_order() {
            return show_order;
        }

        public void setShow_order(String show_order) {
            this.show_order = show_order;
        }

        public String getIdentification() {
            return identification;
        }

        public void setIdentification(String identification) {
            this.identification = identification;
        }

        public int getIs_video() {
            return is_video;
        }

        public void setIs_video(int is_video) {
            this.is_video = is_video;
        }

        public int getIs_show_cate1_icon() {
            return is_show_cate1_icon;
        }

        public void setIs_show_cate1_icon(int is_show_cate1_icon) {
            this.is_show_cate1_icon = is_show_cate1_icon;
        }
    }
}
