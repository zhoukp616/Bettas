package com.zkp.bettas.module.home.all.bean;

import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all.bean
 * @time: 2018/8/24 11:04
 * @description:
 */
public class ThreeCateBean {


    /**
     * error : 0
     * data : [{"id":"168","name":"妹纸主播"},{"id":"32","name":"大神主播"},{"id":"33","name":"王者之路"},{"id":"37","name":"赛事直播"}]
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
         * id : 168
         * name : 妹纸主播
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
