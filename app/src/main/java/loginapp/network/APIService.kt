package loginapp.network

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import loginapp.homemodule.homefragmentmodule.model.VehicalResponse
import loginapp.loginmodule.modelclass.loginmodel.LoginResponse
import loginapp.registermodule.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.*


interface APIService {

    @Headers("Content-Type: application/json")
    @POST("api/v2/people/authenticate")
    fun getLoginAPI(@Body loginObject: JsonObject): Observable<Response<LoginResponse>>

    @Headers("Content-Type: application/json")
    @POST("api/v2/people/create")
    fun getRegisterCall(@Body registerObject: JsonObject): Observable<Response<RegisterResponse>>

    @Headers("Content-Type: application/json")
    @GET("api/v2/vehicles")
    fun plotMarkeronMap(@Header("Authorization") authorization: String): Observable<VehicalResponse>


    companion object {
        const val Base_Url = "https://blooming-stream-45371.herokuapp.com/"
    }
}
