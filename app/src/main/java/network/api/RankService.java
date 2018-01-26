package network.api;

import module.entry.discover.AllareasRankInfo;
import module.entry.discover.OriginalRankInfo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author 小陈
 * @time 2018/1/26  11:24
 * @desc  排行榜相关api
 */
public interface RankService {
    /**
     * 原创排行榜请求
     */
    @GET("index/rank/{type}")
    Observable<OriginalRankInfo> getOriginalRanks(@Path("type") String type);

    /**
     * 全区排行榜数据请求
     */
    @GET("index/rank/{type}")
    Observable<AllareasRankInfo> getAllareasRanks(@Path("type") String type);
}
