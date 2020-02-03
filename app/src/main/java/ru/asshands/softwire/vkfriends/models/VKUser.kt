package ru.asshands.softwire.vkfriends.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class VKUser(
    val id: Int = 0,
    val online: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val city: String = "",
    val photo: String = "",
    val deactivated: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(online)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(city)
        parcel.writeString(photo)
        parcel.writeByte(if (deactivated) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<VKUser> {

        override fun createFromParcel(parcel: Parcel): VKUser {
            return VKUser(parcel)
        }

        override fun newArray(size: Int): Array<VKUser?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject): VKUser {

            var cityTitle = ""

            if (json.has("city")) {
                val cityJsonObj = json.getJSONObject("city")
                cityTitle = cityJsonObj.optString("title", "")
            }


            return VKUser(
                id = json.optInt("id", 0),
                online = json.optInt("online", 0),
                firstName = json.optString("first_name", ""),
                lastName = json.optString("last_name", ""),
                city = cityTitle,
                photo = json.optString("photo_200", "https://vk.com/images/camera_200.png"),
                deactivated = json.optBoolean("deactivated", false)
            )
        }
    }
}