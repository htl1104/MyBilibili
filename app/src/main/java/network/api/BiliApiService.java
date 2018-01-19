package network.api;

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
}
