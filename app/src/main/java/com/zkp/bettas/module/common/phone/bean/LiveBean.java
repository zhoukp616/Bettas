package com.zkp.bettas.module.common.phone.bean;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.common.phone.bean
 * @time: 2018/8/23 9:57
 * @description:
 */
public class LiveBean {

    /**
     * error : 0
     * data : {"hls_url":"http://hls3a.douyucdn.cn/live/2165978r8S6sf4Ks/playlist.m3u8?wsSecret=4ea880ec75265d033724f844374c0dd3&wsTime=1534985431&token=h5-douyu-0-2165978-d71918a0cf89d4b7f0393ea8d61d851b&did=h5_did"}
     */

    private int error;
    private DataBean data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hls_url : http://hls3a.douyucdn.cn/live/2165978r8S6sf4Ks/playlist.m3u8?wsSecret=4ea880ec75265d033724f844374c0dd3&wsTime=1534985431&token=h5-douyu-0-2165978-d71918a0cf89d4b7f0393ea8d61d851b&did=h5_did
         */

        private String hls_url;

        public String getHls_url() {
            return hls_url;
        }

        public void setHls_url(String hls_url) {
            this.hls_url = hls_url;
        }
    }
}
