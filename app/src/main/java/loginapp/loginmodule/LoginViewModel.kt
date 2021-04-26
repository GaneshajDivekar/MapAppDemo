package loginapp.loginmodule

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import loginapp.GenericClass
import loginapp.loginmodule.modelclass.loginmodel.LoginResponse

class LoginViewModel  @ViewModelInject constructor(private val loginRepository: LoginRepository) :
    ViewModel(){
    fun getLoginCall(loginObject: JsonObject): MutableLiveData<GenericClass> {
        return loginRepository.getLoginCall(loginObject)
    }

}