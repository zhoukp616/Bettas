package com.zkp.bettas.module.home.bean;

import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.bean
 * @time: 2018/8/17 10:39
 * @description:
 */
public class VerticalRoomBean {

    /**
     * error : 0
     * data : [{"room_id":"1282190","room_src":"https://rpic.douyucdn.cn/live-cover/appCovers/2018/04/16/1282190_20180416165038_small.jpg","vertical_src":"https://rpic.douyucdn.cn/live-cover/appCovers/2018/04/16/1282190_20180416165038_big.jpg","isVertical":1,"cate_id":201,"room_name":"没有标题 就这样","show_status":"1","subject":"","show_time":"1534473302","owner_uid":"77538371","specific_catalog":"","specific_status":"0","vod_quality":"0","nickname":"鲸鱼欧尼","hn":0,"online":203713,"game_name":"颜值","child_id":581,"avatar_mid":"https://apic.douyucdn.cn/upload/avatar_v3/201808/ace11ce88fe9594bef803e1c7893a8dd_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avatar_v3/201808/ace11ce88fe9594bef803e1c7893a8dd_small.jpg","jumpUrl":"","nrt":0,"ranktype":0,"rmf1":0,"rmf2":0,"rmf3":0,"rmf4":0,"rmf5":0,"show_type":0,"is_noble_rec":0,"anchor_city":"长沙市"},{"room_id":"176341","room_src":"https://rpic.douyucdn.cn/asrpic/180817/176341_1035.jpg","vertical_src":"https://rpic.douyucdn.cn/asrpic/180817/176341_1035.jpg","isVertical":0,"cate_id":311,"room_name":"可达鸭八卦：斗鱼大百科，来吧","show_status":"1","subject":"","show_time":"1534471699","owner_uid":"8709201","specific_catalog":"","specific_status":"0","vod_quality":"0","nickname":"阿凯不才","hn":0,"online":52230,"game_name":"颜值","child_id":0,"avatar_mid":"https://apic.douyucdn.cn/upload/avanew/face/201706/14/17/c60d24aed84762de56e49f1beb69a0a2_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avanew/face/201706/14/17/c60d24aed84762de56e49f1beb69a0a2_small.jpg","jumpUrl":"","nrt":0,"ranktype":0,"rmf1":0,"rmf2":0,"rmf3":0,"rmf4":0,"rmf5":0,"show_type":0,"is_noble_rec":0,"anchor_city":"鱼塘"},{"room_id":"4656563","room_src":"https://rpic.douyucdn.cn/live-cover/appCovers/2018/07/23/4656563_20180723182134_small.jpg","vertical_src":"https://rpic.douyucdn.cn/live-cover/appCovers/2018/07/23/4656563_20180723182134_big.jpg","isVertical":1,"cate_id":201,"room_name":"七夕我陪你一起过吧","show_status":"1","subject":"","show_time":"1534465047","owner_uid":"196773806","specific_catalog":"","specific_status":"0","vod_quality":"0","nickname":"馨丫丫头","hn":0,"online":10636,"game_name":"颜值","child_id":581,"avatar_mid":"https://apic.douyucdn.cn/upload/avatar_v3/201807/c2d85fc3f60b67a5e6c0163cb40f9794_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avatar_v3/201807/c2d85fc3f60b67a5e6c0163cb40f9794_small.jpg","jumpUrl":"","nrt":0,"ranktype":0,"rmf1":0,"rmf2":0,"rmf3":0,"rmf4":0,"rmf5":0,"show_type":0,"is_noble_rec":0,"anchor_city":"成都市"},{"room_id":"5391377","room_src":"https://rpic.douyucdn.cn/asrpic/180817/5391377_1036.jpg","vertical_src":"https://rpic.douyucdn.cn/asrpic/180817/5391377_1036.jpg","isVertical":0,"cate_id":311,"room_name":"李煜喜欢你喜欢的不得了","show_status":"1","subject":"","show_time":"1534468598","owner_uid":"214696475","specific_catalog":"","specific_status":"0","vod_quality":"0","nickname":"李煜这个小仙女儿","hn":0,"online":423677,"game_name":"颜值","child_id":0,"avatar_mid":"https://apic.douyucdn.cn/upload/avatar_v3/201807/eb1a351f41f4840d41329d9061cc9be6_middle.jpg","avatar_small":"https://apic.douyucdn.cn/upload/avatar_v3/201807/eb1a351f41f4840d41329d9061cc9be6_small.jpg","jumpUrl":"","nrt":0,"ranktype":0,"rmf1":0,"rmf2":0,"rmf3":0,"rmf4":0,"rmf5":0,"show_type":0,"is_noble_rec":0,"anchor_city":"鱼塘"}]
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
         * room_id : 1282190
         * room_src : https://rpic.douyucdn.cn/live-cover/appCovers/2018/04/16/1282190_20180416165038_small.jpg
         * vertical_src : https://rpic.douyucdn.cn/live-cover/appCovers/2018/04/16/1282190_20180416165038_big.jpg
         * isVertical : 1
         * cate_id : 201
         * room_name : 没有标题 就这样
         * show_status : 1
         * subject :
         * show_time : 1534473302
         * owner_uid : 77538371
         * specific_catalog :
         * specific_status : 0
         * vod_quality : 0
         * nickname : 鲸鱼欧尼
         * hn : 0
         * online : 203713
         * game_name : 颜值
         * child_id : 581
         * avatar_mid : https://apic.douyucdn.cn/upload/avatar_v3/201808/ace11ce88fe9594bef803e1c7893a8dd_middle.jpg
         * avatar_small : https://apic.douyucdn.cn/upload/avatar_v3/201808/ace11ce88fe9594bef803e1c7893a8dd_small.jpg
         * jumpUrl :
         * nrt : 0
         * ranktype : 0
         * rmf1 : 0
         * rmf2 : 0
         * rmf3 : 0
         * rmf4 : 0
         * rmf5 : 0
         * show_type : 0
         * is_noble_rec : 0
         * anchor_city : 长沙市
         */

        private String room_id;
        private String room_src;
        private String vertical_src;
        private int isVertical;
        private int cate_id;
        private String room_name;
        private String show_status;
        private String subject;
        private String show_time;
        private String owner_uid;
        private String specific_catalog;
        private String specific_status;
        private String vod_quality;
        private String nickname;
        private int hn;
        private int online;
        private String game_name;
        private int child_id;
        private String avatar_mid;
        private String avatar_small;
        private String jumpUrl;
        private int nrt;
        private int ranktype;
        private int rmf1;
        private int rmf2;
        private int rmf3;
        private int rmf4;
        private int rmf5;
        private int show_type;
        private int is_noble_rec;
        private String anchor_city;

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getRoom_src() {
            return room_src;
        }

        public void setRoom_src(String room_src) {
            this.room_src = room_src;
        }

        public String getVertical_src() {
            return vertical_src;
        }

        public void setVertical_src(String vertical_src) {
            this.vertical_src = vertical_src;
        }

        public int getIsVertical() {
            return isVertical;
        }

        public void setIsVertical(int isVertical) {
            this.isVertical = isVertical;
        }

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getShow_status() {
            return show_status;
        }

        public void setShow_status(String show_status) {
            this.show_status = show_status;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getShow_time() {
            return show_time;
        }

        public void setShow_time(String show_time) {
            this.show_time = show_time;
        }

        public String getOwner_uid() {
            return owner_uid;
        }

        public void setOwner_uid(String owner_uid) {
            this.owner_uid = owner_uid;
        }

        public String getSpecific_catalog() {
            return specific_catalog;
        }

        public void setSpecific_catalog(String specific_catalog) {
            this.specific_catalog = specific_catalog;
        }

        public String getSpecific_status() {
            return specific_status;
        }

        public void setSpecific_status(String specific_status) {
            this.specific_status = specific_status;
        }

        public String getVod_quality() {
            return vod_quality;
        }

        public void setVod_quality(String vod_quality) {
            this.vod_quality = vod_quality;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getHn() {
            return hn;
        }

        public void setHn(int hn) {
            this.hn = hn;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public int getChild_id() {
            return child_id;
        }

        public void setChild_id(int child_id) {
            this.child_id = child_id;
        }

        public String getAvatar_mid() {
            return avatar_mid;
        }

        public void setAvatar_mid(String avatar_mid) {
            this.avatar_mid = avatar_mid;
        }

        public String getAvatar_small() {
            return avatar_small;
        }

        public void setAvatar_small(String avatar_small) {
            this.avatar_small = avatar_small;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public int getNrt() {
            return nrt;
        }

        public void setNrt(int nrt) {
            this.nrt = nrt;
        }

        public int getRanktype() {
            return ranktype;
        }

        public void setRanktype(int ranktype) {
            this.ranktype = ranktype;
        }

        public int getRmf1() {
            return rmf1;
        }

        public void setRmf1(int rmf1) {
            this.rmf1 = rmf1;
        }

        public int getRmf2() {
            return rmf2;
        }

        public void setRmf2(int rmf2) {
            this.rmf2 = rmf2;
        }

        public int getRmf3() {
            return rmf3;
        }

        public void setRmf3(int rmf3) {
            this.rmf3 = rmf3;
        }

        public int getRmf4() {
            return rmf4;
        }

        public void setRmf4(int rmf4) {
            this.rmf4 = rmf4;
        }

        public int getRmf5() {
            return rmf5;
        }

        public void setRmf5(int rmf5) {
            this.rmf5 = rmf5;
        }

        public int getShow_type() {
            return show_type;
        }

        public void setShow_type(int show_type) {
            this.show_type = show_type;
        }

        public int getIs_noble_rec() {
            return is_noble_rec;
        }

        public void setIs_noble_rec(int is_noble_rec) {
            this.is_noble_rec = is_noble_rec;
        }

        public String getAnchor_city() {
            return anchor_city;
        }

        public void setAnchor_city(String anchor_city) {
            this.anchor_city = anchor_city;
        }
    }
}
