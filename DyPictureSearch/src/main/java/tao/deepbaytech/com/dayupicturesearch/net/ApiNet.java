package tao.deepbaytech.com.dayupicturesearch.net;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;
import tao.deepbaytech.com.dayupicturesearch.entity.BaseResponse;
import tao.deepbaytech.com.dayupicturesearch.entity.ImgSearchEntity;
import tao.deepbaytech.com.dayupicturesearch.entity.XSdz;

/**
 * @author IT烟酒僧
 * created   2018/12/19 16:05
 * desc:
 */
public interface ApiNet {
    @FormUrlEncoded
    @POST("search-picture")
    Observable<ImgSearchEntity> postImgSearch(@FieldMap Map<String, Object> fieldMap);

    @Multipart
    @POST() Observable<BaseResponse> postImg(
            @Url String url,
            @Part MultipartBody.Part file );

    @Multipart @POST("search-picture") Observable<ImgSearchEntity> postImgSearch(
            @Part MultipartBody.Part file ,@Query("nowTime")long nowTime);

    @FormUrlEncoded
    @POST() Observable<XSdz> postPreSearch(
            @Url String url,
            @Field("tfsid") String tfsid);
}
