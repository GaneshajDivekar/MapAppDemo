package loginapp.loginmodule.modelclass.loginmodel


import com.google.gson.annotations.SerializedName

data class LoginError(
    @SerializedName("error_code")
    val errorCode: Int,
    val message: String
)