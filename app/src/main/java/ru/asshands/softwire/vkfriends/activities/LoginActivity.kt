package ru.asshands.softwire.vkfriends.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter

import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils
import kotlinx.android.synthetic.main.activity_login.*
import ru.asshands.softwire.vkfriends.presenters.LoginPresenter
import ru.asshands.softwire.vkfriends.views.LoginView


class LoginActivity : MvpAppCompatActivity(), LoginView {
    private val TAG = this::class.java.simpleName
    private lateinit var mCpvWait: CircularProgressView
    private lateinit var mBtnEnter: Button
    private lateinit var mTxtHello: TextView

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ru.asshands.softwire.vkfriends.R.layout.activity_login)


        mTxtHello = txt_login_hello
        mBtnEnter = btn_login_enter
        mCpvWait = cpv_login

        mBtnEnter.setOnClickListener {
            VK.login(this, listOf(VKScope.FRIENDS))
            //    loginPresenter.login(true)
        }

        //     val fingerprints = VKUtils.getCertificateFingerprint(this, this.packageName)
        //     Log.d(TAG,"fingerprint: ${fingerprints!!.first().toString()}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (loginPresenter.loginVk(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    override fun startLoading() {
        mBtnEnter.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }


    override fun endLoading() {
        mBtnEnter.visibility = View.VISIBLE
        mCpvWait.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_LONG).show()
    }

    override fun openFriends() {
        startActivity(Intent(applicationContext, FriendsActivity::class.java))
    }

}
