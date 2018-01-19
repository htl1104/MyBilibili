package network.api;

import module.entry.video.HDVideoInfo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author 小陈
 * @time 2018/1/19  14:12
 * bilibili-go相关api
 */
public interface BiliGoService {

    /**
     * b站高清视频
     * quailty:清晰度(1~2，根据视频有不同)
     * type: 格式(mp4/flv)
     */
    @GET("/video/{cid}")
    Observable<HDVideoInfo> getHDVideoUrl(@Path("cid") int cid,
                                          @Query("quailty") int quailty,
                                          @Query("type") String type);

    /**
     * 新番连载
     */
//    @GET("bangumi")
//    Observable<NewBangumiSerialInfo> getNewBangumiSerialList();
    
}
