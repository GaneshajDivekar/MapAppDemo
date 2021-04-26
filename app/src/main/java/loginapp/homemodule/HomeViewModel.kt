package loginapp.homemodule

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import loginapp.homemodule.homefragmentmodule.model.VehicalResponse
import loginapp.registermodule.RegisterRepository

class HomeViewModel @ViewModelInject constructor(private val homeRepository: HomeRepository) :
    ViewModel(){
    fun plotMarkeronMap(authToken: String): MutableLiveData<VehicalResponse> {
        return homeRepository.plotMarkeronMap(authToken)
    }

}