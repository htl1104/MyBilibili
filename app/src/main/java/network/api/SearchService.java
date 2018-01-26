package network.api;

import entity.discover.HotSearchTag;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author 小陈
 * @time 2018/1/26  17:53
 * @desc 搜索相关api
 */
public interface SearchService {
    /**
     * 首页发现热搜词
     */
    @GET("main/hotword?access_key=ec0f54fc369d8c104ee1068672975d6a&actionKey=appkey&appkey=27eb53fc9058f8c3")
    Observable<HotSearchTag> getHotSearchTags();
}
