package ru.asshands.softwire.vkfriends.providers

import android.os.Handler
import android.util.Log
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.exceptions.VKApiExecutionException
import ru.asshands.softwire.vkfriends.R
import ru.asshands.softwire.vkfriends.models.FriendModel
import ru.asshands.softwire.vkfriends.models.VKUser
import ru.asshands.softwire.vkfriends.presenters.FriendsPresenter

class FriendsProvider(var presenter: FriendsPresenter) {
    fun testLoadFriends(hasFriends: Boolean) {
        Handler().postDelayed({
            val friendsList: ArrayList<FriendModel> = ArrayList()
            if (hasFriends) {
                val user1 = FriendModel(
                    "Ivan",
                    "Petrov",
                    null,
                    "https://www.belcanto.ru/media/images/publication/petrov_ivan.jpg",
                    true
                )

                val user2 = FriendModel(
                    "Alexey",
                    "Gladkov",
                    "Tomsk",
                    "https://sun9-53.userapi.com/c851420/v851420152/16b384/yav0qJ8xjag.jpg",
                    true
                )

                val user3 = FriendModel(
                    "Egor",
                    "Sidorov",
                    "Moscow",
                    "https://sun9-41.userapi.com/c850424/v850424678/56bb3/A-zDVKwXJ8g.jpg",
                    true
                )

                friendsList.add(user1)
                friendsList.add(user2)
                friendsList.add(user3)

            }
            //presenter.friendsLoaded(friendsList)
        }, 2000)

    }


    fun loadFriends() {
        VK.execute(VKFriendsRequest(25464828),object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
            presenter.friendsLoaded(result)
                Log.d("TAG-LIST",result.toString())
            }

            override fun fail(error: VKApiExecutionException) {
                //On Error:
                Log.d("TAG-LIST","произошло некторое дерьмо)")
                presenter.showError(R.string.friends_error_loading)
            }
        })
    }
}