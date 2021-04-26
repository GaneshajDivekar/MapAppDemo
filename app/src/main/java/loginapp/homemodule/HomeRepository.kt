package loginapp.homemodule

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import loginapp.GenericClass
import loginapp.homemodule.homefragmentmodule.model.VehicalResponse
import loginapp.loginmodule.modelclass.loginmodel.LoginError
import loginapp.network.APIService
import loginapp.registermodule.model.RegisterGenericClass
import loginapp.registermodule.model.RegisterResponse
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiHelper: APIService) {
    fun plotMarkeronMap(authToken: String): MutableLiveData<VehicalResponse> {
        val plotMarkeronMapLiveData=MutableLiveData<VehicalResponse>()
        apiHelper.plotMarkeronMap(authToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { postResult ->
                    plotMarkeronMapLiveData.postValue(postResult)
                },
                { error ->
                    plotMarkeronMapLiveData.postValue(null)
                }
            )
        return plotMarkeronMapLiveData
    }


}