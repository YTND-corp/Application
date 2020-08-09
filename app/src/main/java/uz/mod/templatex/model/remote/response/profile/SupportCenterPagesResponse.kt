package uz.mod.templatex.model.remote.response.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class SupportCenterPagesResponse(val pages: List<Page>)

@Parcelize
data class Page(
    val id: Long,
    val title: String,
    val content: String,
    val image: String,
    @SerializedName("is_attached")
    val isAttached: Boolean
) : Parcelable