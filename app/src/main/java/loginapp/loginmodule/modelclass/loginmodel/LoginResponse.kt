package loginapp.loginmodule.modelclass.loginmodel


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("authentication_token")
    val authenticationToken: String,
    val person: Person

)