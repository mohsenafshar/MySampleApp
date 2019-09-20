package ir.mohsenafshar.mysampleapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Waybill(
    val origin: String,
    val destination: String,
    val price: BigDecimal,
    val weight: Double,
    val packagingType: String,
    val consignment: String,
    val loadedDate: String
): Parcelable