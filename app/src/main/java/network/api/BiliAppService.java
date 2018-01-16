package network.api;

import module.entry.recommend.RecommendBannerInfo;
import module.entry.recommend.RecommendInfo;
import retrofit2.http.GET;
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
    
}
