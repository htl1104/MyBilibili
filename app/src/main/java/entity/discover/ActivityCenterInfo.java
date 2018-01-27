package entity.discover;

import java.util.List;

/**
 * @author 小陈
 * @time 2018/1/27  10:32
 * @desc 活動中心模型類
 */
public class ActivityCenterInfo {


    /**
     * code : 0
     * list : [{"title":"Jingle Beats","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20171223/29mx5r1pzy.png","link":"http://www.bilibili.com/blackboard/activity-xmas2017.html","state":1},{"title":"寒假作业：拜年记【bilibilil专栏】","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20180123/z48y4jon23.jpg","link":"http://www.bilibili.com/blackboard/activity-HJttrKEHf.html","state":0},{"title":"《明星大侦探》的N种打开方式","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20171228/89vkw87jo2.jpg","link":"http://www.bilibili.com/blackboard/activity-Sk8qwOkXG.html","state":1},{"title":"我永远喜欢________________ 第一期【bilibilil专栏】征稿活动开始啦","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20171220/p6x9pyk16m.jpg","link":"http://www.bilibili.com/blackboard/activity-B1dUtFwGz.html","state":1},{"title":"Kira☆Kira明星仿妆大比拼","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20171218/59przp0lyk.jpg","link":"http://www.bilibili.com/blackboard/activity-rJyJunkMf.html","state":1},{"title":"bilibili2017年度弹幕大公开","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20171218/59prko5yzx.jpg","link":"http://www.bilibili.com/blackboard/dannmaku2017-m.html","state":1},{"title":"BBC earth 60s 我的美丽新世界","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20171124/p6z62zq0m7.jpg","link":"http://www.bilibili.com/blackboard/activity-BBC60-m.html","state":1},{"title":"一球成名","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20171124/k7r79qox13.jpg","link":"http://www.bilibili.com/blackboard/activity-HypD2Grgf.html","state":1},{"title":"剧场版 《Fate/stay night [Heaven's Feel] I. presage flower》 观影报名","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20171121/w4m16n206k.jpg","link":"http://www.bilibili.com/blackboard/fhf-ticket-m.html","state":1},{"title":"全民小埋体操","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20171031/y4ok7xqkmz.png","link":"http://www.bilibili.com/blackboard/activity-SkPrjvE0b.html","state":1},{"title":"Double； 7 七夕之约","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20170827/o68z0p1lo9.png","link":"http://www.bilibili.com/blackboard/double7.html","state":1},{"title":"呵呵呵表情包乱逗","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20170913/296vl9j44p.jpg","link":"http://www.bilibili.com/blackboard/activity-meme1st-h5.html","state":1},{"title":"bilibili Moe 2017 动画角色人气大赏 - 日本动画场","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20170609/69oy191qrk.jpg","link":"http://bangumi.bilibili.com/moe/2017/jp/mobile/","state":1},{"title":"bilibili Moe 2017 动画角色人气大赏 - 国产动画场","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20170609/p6q35ov5lx.jpg","link":"http://bangumi.bilibili.com/moe/2017/cn/mobile/","state":1},{"title":"bilibili游戏区超还原大赛","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20170731/w4p2r90jo8.jpg","link":"http://www.bilibili.com/blackboard/activity-ByaLbQ3IZ.html","state":1},{"title":"10th·初音未来诞生祭","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20170815/29yymymyyr.jpg","link":"http://www.bilibili.com/blackboard/activity-Miku10th-m.html","state":1},{"title":"创想无双\u2014\u2014bilibili新作大乱斗","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20170607/x65262y6lw.jpg","link":"http://www.bilibili.com/blackboard/activity-Original1st-m.html","state":1},{"title":"Bilibili Dancing Festival 2017","cover":"http://activity.hdslb.com/blackboard/cover/9004v57z9y.jpg","link":"http://www.bilibili.com/blackboard/activity-BDF2017-m.html","state":1},{"title":"童年地下城之拯救小电视","cover":"http://i0.hdslb.com/bfs/activity-plat/cover/20170531/x65r10rznz.jpg","link":"http://www.bilibili.com/blackboard/activity-61dungeon.html","state":1},{"title":"bilibili 2017 五·一脑洞节","cover":"http://activity.hdslb.com/blackboard/cover/j66k95rp03.jpg","link":"http://www.bilibili.com/blackboard/activity-Mindblowing2017-m.html","state":1}]
     * total : 59
     * pages : 3
     */

    private int code;
    private int total;
    private int pages;
    private List<ListBean> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * title : Jingle Beats
         * cover : http://i0.hdslb.com/bfs/activity-plat/cover/20171223/29mx5r1pzy.png
         * link : http://www.bilibili.com/blackboard/activity-xmas2017.html
         * state : 1
         */

        private String title;
        private String cover;
        private String link;
        private int state;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
