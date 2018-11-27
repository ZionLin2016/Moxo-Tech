package cn.lsd.app.api.remote;

import cn.lsd.app.model.response.bk.BKResult;
import cn.lsd.app.model.response.member.MemberItem;
import cn.lsd.app.ui.groupclass.model.ClassList;
import cn.lsd.app.ui.groupclass.model.UserInfo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSD on 2017/5/11.
 */

public interface UserApi {
    @GET("LoginServlet")
    Observable<BKResult<UserInfo>> getLogin(
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("RegisterServlet")
    Observable<BKResult<UserInfo>> getRegister(@Query("username,password") String username, String password);
    @POST("add.do")
    Observable<BKResult<UserInfo>> getBKAdd(@Body ClassList classJson);

    @GET("MemberListServlet")
    Observable<BKResult<List<MemberItem>>> getAllMember(@Query("username") String username);
}