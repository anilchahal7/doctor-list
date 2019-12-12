package `in`.iceberg.domain.model

data class Doctor (
    val id: String,
    val name: String,
    val photoId: String,
    val rating: Double = 0.0,
    val address: String,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val highlighted: Boolean,
    val reviewCount: Int,
    val specialityIds : Array<Int>? = null,
    val source: String,
    val phoneNumber: String,
    val email: String,
    val website: String,
    val openingHours: Array<String>? = null,
    val integration: String,
    val translation: String
)