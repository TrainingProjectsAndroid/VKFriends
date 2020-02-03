package ru.asshands.softwire.vkfriends.presenters


import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.asshands.softwire.vkfriends.R
import ru.asshands.softwire.vkfriends.models.FriendModel
import ru.asshands.softwire.vkfriends.models.VKUser
import ru.asshands.softwire.vkfriends.providers.FriendsProvider
import ru.asshands.softwire.vkfriends.views.FriendsView

@InjectViewState
class FriendsPresenter : MvpPresenter<FriendsView>() {
    fun loadFriends() {
        viewState.startLoading()
        FriendsProvider(this).loadFriends()
    }

    fun friendsLoaded(friendsList: List<VKUser>) {
        viewState.endLoading()
        if (friendsList.isEmpty()) {
            viewState.setupEmptyList()
            viewState.showError(R.string.friends_no_items)
        } else {
            viewState.setupFriendsList(friendsList)
        }
    }

    fun showError(textResource: Int){
        viewState.showError(textResource)
    }
}