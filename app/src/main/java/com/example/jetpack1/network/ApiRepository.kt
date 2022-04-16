package com.example.jetpack1.network
import com.chivox.online.network.RetrofitManager
import com.example.jetpack1.User
import com.example.jetpack1.constant.ApiConstant
import com.example.jetpack1.network.entity.ArticleList
import com.example.jetpack1.network.entity.PassportToken
import com.example.jetpack1.network.entity.Response
import retrofit2.http.*

interface ApiRepository {

    // 和协程联用
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): Response<ArticleList>

    @GET("api/gateway")
    @Headers(RetrofitManager.BASE_URL_NAME_KEY + ApiConstant.KEY_GATE_URL)
    suspend fun gateway(@QueryMap map: MutableMap<String, Any>): @JvmSuppressWildcards Response<Any>

    @FormUrlEncoded
    @POST("/api/students/login")
    @Headers(RetrofitManager.BASE_URL_NAME_KEY + ApiConstant.KEY_MOBILE_URL)
    suspend fun login(@FieldMap map: MutableMap<String, Any>?): Response<User?>

    @FormUrlEncoded
    @POST("api/passport/token")
    @Headers(RetrofitManager.BASE_URL_NAME_KEY + ApiConstant.KEY_TOKEN_URL)
    suspend fun passportToken(@FieldMap map: MutableMap<String, Any>): Response<PassportToken>

}