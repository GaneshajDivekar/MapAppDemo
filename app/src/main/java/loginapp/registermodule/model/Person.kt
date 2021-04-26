package loginapp.registermodule.model


import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("app_info_keys")
    val appInfoKeys: List<Any>,
    @SerializedName("display_name")
    val displayName: String,
    val key: String,
    val role: Role
)