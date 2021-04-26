package loginapp.registermodule.model


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("authentication_token")
    val authenticationToken: String,
    val person: Person
)