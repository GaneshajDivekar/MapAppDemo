package loginapp.loginmodule.modelclass.loginmodel


import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("display_name")
    val displayName: String,
    val key: String,
    val role: Role
)