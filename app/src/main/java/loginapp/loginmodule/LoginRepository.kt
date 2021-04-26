package loginapp.loginmodule

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import loginapp.GenericClass
import loginapp.loginmodule.modelclass.loginmodel.LoginError
import loginapp.loginmodule.modelclass.loginmodel.LoginResponse
import loginapp.network.APIService
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apiHelper: APIService
){
    fun getLoginCall(loginObject: JsonObject): MutableLiveData<GenericClass> {
        val loginLiveData=MutableLiveData<GenericClass>()

        apiHelper.getLoginAPI(loginObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { postResult ->
                    if(postResult.isSuccessful)
                    {
                        val loginResponse = postResult.body()
                        val genericClass=GenericClass(loginResponse?.authenticationToken,loginResponse?.person,"")
                        loginLiveData.postValue(genericClass)
                    }else{
                        val gson = Gson()
                        val type = object : TypeToken<LoginError>() {}.type
                        var errorResponse: LoginError = gson.fromJson(postResult.errorBody()?.charStream(), type)
                        val genericClass=GenericClass(msg = errorResponse.message)
                        loginLiveData.postValue(genericClass)
                    }
                }
            )
        return loginLiveData
    }
}