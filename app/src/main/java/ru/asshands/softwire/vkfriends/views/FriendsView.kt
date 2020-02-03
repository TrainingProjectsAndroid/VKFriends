package ru.asshands.softwire.vkfriends.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.asshands.softwire.vkfriends.models.FriendModel
import ru.asshands.softwire.vkfriends.models.VKUser

@StateStrategyType(AddToEndSingleStrategy::class)
interface FriendsView : MvpView {
    fun showError(textResource: Int)
    fun setupEmptyList()
    fun setupFriendsList(friendsList: List<VKUser>)
    fun startLoading()
    fun endLoading()
}