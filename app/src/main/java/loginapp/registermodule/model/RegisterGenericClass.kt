package loginapp.registermodule.model

import com.google.gson.annotations.SerializedName

data class RegisterGenericClass
(
    val authenticationToken: String?=null,
    val person: Person?=null,
    val msg :String
)