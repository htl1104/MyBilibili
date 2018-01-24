package entity.bangumi;

import java.util.List;

/**
 * @author 小陈
 * @time 2018/1/24  10:44
 * @desc 番剧索引
 */
public class BangumiIndexInfo {


    /**
     * code : 0
     * message : success
     * result : {"category":[{"cover":"http://i0.hdslb.com/bfs/bangumi/3f5c2e411482303a19870c66f27c22ad7037c9a9.gif","tag_id":"117","tag_name":"轻改"},{"cover":"http://i0.hdslb.com/bfs/bangumi/14146570a16a6aea17a102de52cd8e5a7c9ba050.jpg","tag_id":"81","tag_name":"萌系"},{"cover":"http://i0.hdslb.com/bfs/bangumi/42ddee901fc4a27afcc01f0f210c17a6819ce9f9.jpg","tag_id":"70","tag_name":"搞笑"},{"cover":"http://i0.hdslb.com/bfs/bangumi/1d703634cd3ee35b625bf882f27289db301cae63.jpg","tag_id":"20","tag_name":"热血"},{"cover":"http://i0.hdslb.com/bfs/bangumi/5294b9159ebee07cee4ee43a754b7b8ac10c415d.jpg","tag_id":"104","tag_name":"催泪"},{"cover":"http://i0.hdslb.com/bfs/bangumi/43ac179a832c76b7924f1d6f2909a1b346220a4b.jpg","tag_id":"5","tag_name":"后宫"},{"cover":"http://i0.hdslb.com/bfs/bangumi/d61d3de9bc37d71a1e56c7637012732f52b11a5d.jpg","tag_id":"105","tag_name":"机战"},{"cover":"http://i0.hdslb.com/bfs/bangumi/98cca8bd39d1fe897dcd87c730ad2cb3b3acf51c.jpg","tag_id":"82","tag_name":"基腐"},{"cover":"http://i0.hdslb.com/bfs/bangumi/d48ef57040ceff2ccbbe6b1e32f9e4cc77d9712d.jpg","tag_id":"110","tag_name":"恋爱"},{"cover":"http://i0.hdslb.com/bfs/bangumi/4314ed93392ad2370814808f662025dad6e27e32.jpg","tag_id":"6","tag_name":"百合"},{"cover":"http://i0.hdslb.com/bfs/bangumi/40f8b2e6912857cbb8432554806cd9157fc7674a.jpg","tag_id":"125","tag_name":"伪娘"},{"cover":"http://i0.hdslb.com/bfs/bangumi/e87f205bf159de4a8c601a54e2671d17ba853257.jpg","tag_id":"71","tag_name":"科幻"},{"cover":"http://i0.hdslb.com/bfs/bangumi/261cee5f42fc41ca6c204bb749fab96e3df41891.jpg","tag_id":"115","tag_name":"乙女"},{"cover":"http://i0.hdslb.com/bfs/bangumi/97b47909a4f782cde760d8c9e4cce2642fcd3cef.jpg","tag_id":"57","tag_name":"奇幻"},{"cover":"http://i0.hdslb.com/bfs/bangumi/c5ee0980e0c00749c8652bd8109fc72a7aa9563d.jpg","tag_id":"124","tag_name":"推理"},{"cover":"http://i0.hdslb.com/bfs/bangumi/18317b1d3c2af5143066f81adeb3f149f6bf14dc.jpg","tag_id":"72","tag_name":"音乐"},{"cover":"http://i0.hdslb.com/bfs/bangumi/8d2f97f90a50f30ac500507a8053a2abec32a91a.jpg","tag_id":"93","tag_name":"校园"},{"cover":"http://i0.hdslb.com/bfs/bangumi/6fbc0e622b3f3feb77f55104664ce87c8c45a664.jpg","tag_id":"121","tag_name":"偶像"},{"cover":"http://i0.hdslb.com/bfs/bangumi/5ef199ba7023752c799845164d2836f6e9735a05.jpg","tag_id":"127","tag_name":"社团"},{"cover":"http://i0.hdslb.com/bfs/bangumi/dc26d656635a86e7212ea9a000859c86c994d55c.jpg","tag_id":"23","tag_name":"运动"},{"cover":"http://i0.hdslb.com/bfs/bangumi/56fe917b62d4c5dc7446e80eb8c009913168c849.jpg","tag_id":"9","tag_name":"少女"},{"cover":"http://i0.hdslb.com/bfs/bangumi/8790ca30ddd9f174d7d1b577df563ac042c49d07.jpg","tag_id":"94","tag_name":"装逼"},{"cover":"http://i0.hdslb.com/bfs/bangumi/46b268fbb334239ec9fef61a4bdf4be161087e1f.jpg","tag_id":"103","tag_name":"智斗"},{"cover":"http://i0.hdslb.com/bfs/bangumi/47a8891bfdc0d2d4a8f3bcd5b01dd99848aeca43.jpg","tag_id":"95","tag_name":"战斗"},{"cover":"http://i0.hdslb.com/bfs/bangumi/ac9636df3fe24d3699e3bd03e56625862760fa00.jpg","tag_id":"16","tag_name":"日常"},{"cover":"http://i0.hdslb.com/bfs/bangumi/3ac5e08154bb51caf4db6a54efbe44b20d28e9a0.jpg","tag_id":"122","tag_name":"魔法"},{"cover":"http://i0.hdslb.com/bfs/bangumi/29baa04d18505c775c131e0d0db0bf8704cc61bb.jpg","tag_id":"21","tag_name":"治愈"},{"cover":"http://i0.hdslb.com/bfs/bangumi/c7f7b90024be65a8056040585cfe3b4d86316b8b.jpg","tag_id":"98","tag_name":"声控"},{"cover":"http://i0.hdslb.com/bfs/bangumi/3c68dd7856c566dc691bf1f97b866b618f6e79bc.jpg","tag_id":"44","tag_name":"泡面"},{"cover":"http://i0.hdslb.com/bfs/bangumi/f769db95d13a381818359fc83b81a0989db0336f.jpg","tag_id":"67","tag_name":"历史"},{"cover":"http://i0.hdslb.com/bfs/bangumi/f04325ea11aaad0be3315b0bf9efbc87329f9cb9.jpg","tag_id":"87","tag_name":"猎奇"},{"cover":"http://i0.hdslb.com/bfs/bangumi/41fdc136e92ebfa6781f017695c686797627ffbc.jpg","tag_id":"22","tag_name":"致郁"},{"cover":"http://i0.hdslb.com/bfs/bangumi/13e97718ac1a6011f4a7c2b5aa07db19add776df.jpg","tag_id":"88","tag_name":"时泪"},{"cover":"http://i0.hdslb.com/bfs/bangumi/0a89e6fc2da1a8f714ef3872d3b58df7137eb195.jpg","tag_id":"106","tag_name":"美食"},{"cover":"http://i0.hdslb.com/bfs/bangumi/1e46b1a14463a7813c23f04918b5c94073b8b00b.jpg","tag_id":"24","tag_name":"少儿"},{"cover":"http://i0.hdslb.com/bfs/bangumi/3464a027c00f3e56d813fe7fa604a639fd3c99e0.jpg","tag_id":"138","tag_name":"励志"},{"cover":"http://i0.hdslb.com/bfs/bangumi/b41042c3d478946cb4f23aab24b61987d8ed4992.jpg","tag_id":"140","tag_name":"职场"},{"cover":"http://i0.hdslb.com/bfs/bangumi/d3618f2c1e6c59d94c09965421f92443d435e373.jpg","tag_id":"139","tag_name":"神魔"},{"cover":"http://i0.hdslb.com/bfs/bangumi/e27f4841205f823c309237dcf0109b791b093972.jpg","tag_id":"141","tag_name":"萝莉"},{"cover":"http://i0.hdslb.com/bfs/bangumi/3121473d5dd03a9bcccb8490034207e724e731b3.jpg","tag_id":"135","tag_name":"漫改"},{"cover":"http://i0.hdslb.com/bfs/bangumi/293b57540e40582051f63239363253f7fa15795f.jpg","tag_id":"137","tag_name":"原创"},{"cover":"http://i0.hdslb.com/bfs/bangumi/bc37ef56c0ae05146d0d272fe7617025a27c96a6.jpg","tag_id":"136","tag_name":"游戏改"}],"years":["1928","1957","1958","1961","1962","1963","1969","1970","1972","1973","1974","1975","1976","1977","1978","1979","1980","1981","1982","1983","1984","1985","1986","1987","1988","1989","1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019"]}
     */

    private int code;
    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<CategoryBean> category;
        private List<String> years;

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<String> getYears() {
            return years;
        }

        public void setYears(List<String> years) {
            this.years = years;
        }

        public static class CategoryBean {
            /**
             * cover : http://i0.hdslb.com/bfs/bangumi/3f5c2e411482303a19870c66f27c22ad7037c9a9.gif
             * tag_id : 117
             * tag_name : 轻改
             */

            private String cover;
            private String tag_id;
            private String tag_name;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getTag_id() {
                return tag_id;
            }

            public void setTag_id(String tag_id) {
                this.tag_id = tag_id;
            }

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }
        }
    }
}
