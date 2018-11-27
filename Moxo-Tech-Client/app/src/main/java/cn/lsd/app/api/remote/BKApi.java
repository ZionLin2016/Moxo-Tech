package cn.lsd.app.api.remote;

import cn.lsd.app.model.record.BkRecord;
import cn.lsd.app.model.response.bk.BKItem;
import cn.lsd.app.model.response.bk.BKResult;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSD on 2017/4/22.
 */

public interface BKApi {
    @GET("ClassListServlet")
    Observable<BKResult<List<BKItem>>> getBKList(@Query("username") String username);

    @GET("DelClassServlet")
    Observable<BKResult<List<BKItem>>> delBk(@Query("invite_code") String code);

    @POST("ClassListServlet")
    Observable<BKResult<BKItem>> getBKAdd(@Body BkRecord bkRecord);
}
