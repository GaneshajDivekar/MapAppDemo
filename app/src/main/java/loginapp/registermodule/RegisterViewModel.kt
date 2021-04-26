package loginapp.registermodule

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import loginapp.GenericClass
import loginapp.loginmodule.LoginRepository
import loginapp.registermodule.model.RegisterGenericClass
import loginapp.registermodule.model.RegisterResponse

class RegisterViewModel @ViewModelInject constructor(private val registerRepository: RegisterRepository) :
    ViewModel(){

    fun getRegisterCall(registerObject: JsonObject): MutableLiveData<RegisterGenericClass> {
            return registerRepository.getRegisterCall(registerObject)
        }
}