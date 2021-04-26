package loginapp.registermodule

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import loginapp.GenericClass
import loginapp.loginmodule.modelclass.loginmodel.LoginError
import loginapp.network.APIService
import loginapp.registermodule.model.RegisterGenericClass
import loginapp.registermodule.model.RegisterResponse
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val apiHelper: APIService) {
    fun getRegisterCall(registerObject: JsonObject): MutableLiveData<RegisterGenericClass> {
        val registerLiveData=MutableLiveData<RegisterGenericClass>()
        apiHelper.getRegisterCall(registerObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { postResult ->
                    if(postResult.isSuccessful)
                    {
                        val registerResponse = postResult.body()
                        val genericClass= RegisterGenericClass(registerResponse?.authenticationToken,registerResponse?.person,"")
                        registerLiveData.postValue(genericClass)
                    }else{
                        val gson = Gson()
                        val type = object : TypeToken<LoginError>() {}.type
                        var errorResponse: LoginError = gson.fromJson(postResult.errorBody()?.charStream(), type)
                        val genericClass= RegisterGenericClass(msg = errorResponse.message)
                        registerLiveData.postValue(genericClass)
                    }
                }
            )
        return registerLiveData
    }


}