package network.api;

import entity.discover.ActivityCenterInfo;
import entity.region.RegionDetailsInfo;
import entity.region.RegionRecommendInfo;
import entity.video.VideoDetailsInfo;
import module.entry.recommend.RecommendBannerInfo;
import module.entry.recommend.RecommendInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author 小陈
 * @time 2018/1/16  15:46
 * @desc ${TODD}
 */
public interface BiliAppService {

    /**
     * 首页推荐数据
     */
    @GET("x/show/old?platform=android&device=&build=412001")
    Observable<RecommendInfo> getRecommendedInfo();

    /**
     * 首页推荐banner
     */
    @GET("x/banner?plat=4&build=411007&channel=bilih5")
    Observable<RecommendBannerInfo> getRecommendedBannerInfo();

    /**
     * 视频详情数据
     */
    @GET("x/view?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&sign=1206255541e2648c1badb87812458046&ts=1478349831")
    Observable<VideoDetailsInfo> getVideoDetails(@Query("aid") int aid);


    /**
     * 分区推荐
     */
    @GET("x/v2/region/show?access_key=67cbf6a1e9ad7d7f11bfbd918e50c837&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3600&device=phone&mobi_app=iphone&plat=1&platform=ios&sign=959d7b8c09c65e7a66f7e58b1a2bdab9&ts=1472310694")
    Observable<RegionRecommendInfo> getRegionRecommends(@Query("rid") int rid);


    /**
     * 分区类型详情
     */
    @GET("x/v2/region/show/child?build=3600")
    Observable<RegionDetailsInfo> getRegionDetails(@Query("rid") int rid);

    /**
     * 话動中心
     */
    @GET("event/getlist?device=phone&mobi_app=iphone")
    Observable<ActivityCenterInfo> getActivityCenterList(
            @Query("page") int page, @Query("pagesize") int pageSize);
    
}
