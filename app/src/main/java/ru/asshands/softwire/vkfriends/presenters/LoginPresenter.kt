package ru.asshands.softwire.vkfriends.presenters

import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.core.os.postDelayed
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import ru.asshands.softwire.vkfriends.R
import ru.asshands.softwire.vkfriends.views.LoginView

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {
    fun login(isSuccess: Boolean) {
        viewState.startLoading()
        Handler().postDelayed({
            viewState.endLoading()
            if (isSuccess) {
                viewState.openFriends()
            } else {
                viewState.showError(R.string.login_error_credentions)
            }
        }, 1500)

    }

    fun loginVk(requestCode: Int, resultCode: Int, data: Intent?): Boolean {

        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                viewState.openFriends()
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
                viewState.showError(R.string.login_error_credentions)
            }
        }
        return (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback))
    }


}