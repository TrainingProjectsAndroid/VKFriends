package ru.asshands.softwire.vkfriends.providers

import android.util.Log
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject
import ru.asshands.softwire.vkfriends.models.VKUser

class VKFriendsRequest(uid: Int = 0): VKRequest<List<VKUser>>("friends.get") {
    init {
        if (uid != 0) {
            addParam("user_id", uid)
        }
        addParam("fields", "photo_200, city, quotes")
    }

    override fun parse(r: JSONObject): List<VKUser> {

        Log.d("TAG-LIST:JSONObj:",r.toString())
        val users = r.getJSONObject("response").getJSONArray("items")

        Log.d("TAG-LIST:JSONArray:",users.toString())
        val result = ArrayList<VKUser>()
        for (i in 0 until users.length()) {
            result.add(VKUser.parse(users.getJSONObject(i)))
        }
        Log.d("TAG-LIST:List<VKUser>:",result.toString())
        return result
    }
}