package network.api;

import entity.discover.ActivityCenterInfo;
import entity.discover.TopicCenterInfo;
import entity.video.VideoCommentInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author 小陈
 * @time 2018/1/19  10:36
 * @desc ${TODD}
 */
public interface BiliApiService {
    /**
     * 视频评论
     */
    @GET("feedback")
    Observable<VideoCommentInfo> getVideoComment(
            @Query("aid") int aid,
            @Query("page") int page, @Query("pagesize") int pageSize, @Query("ver") int ver);

    /**
     * 活動中心
     */
    @GET("event/getlist?device=phone&mobi_app=iphone")
    Observable<ActivityCenterInfo> getActivityCenterList(
            @Query("page") int page, @Query("pagesize") int pageSize);

    /**
     * 话题中心
     */
    @GET("topic/getlist?device=phone&mobi_app=iphone&page=1&pagesize=137")
    Observable<TopicCenterInfo> getTopicCenterList();
}
