package loginapp

import com.google.gson.annotations.SerializedName
import loginapp.loginmodule.modelclass.loginmodel.Person

data class GenericClass
(
    val authenticationToken: String?=null,
    val person: Person?=null,
    val msg :String
)