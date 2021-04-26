package loginapp.homemodule.homefragmentmodule.model


import com.google.gson.annotations.SerializedName

data class VehicalResponseItem(
    val id: Int,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_available")
    val isAvailable: Boolean,
    val lat: Double,
    @SerializedName("license_plate_number")
    val licensePlateNumber: String,
    val lng: Double,
    @SerializedName("remaining_mileage")
    val remainingMileage: Int,
    @SerializedName("remaining_range_in_meters")
    val remainingRangeInMeters: Int,
    @SerializedName("transmission_mode")
    val transmissionMode: String,
    @SerializedName("vehicle_make")
    val vehicleMake: String,
    @SerializedName("vehicle_pic_absolute_url")
    val vehiclePicAbsoluteUrl: String,
    @SerializedName("vehicle_type")
    val vehicleType: String,
    @SerializedName("vehicle_type_id")
    val vehicleTypeId: Int
)