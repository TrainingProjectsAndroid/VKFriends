package ru.asshands.softwire.vkfriends.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.rahatarmanahmed.cpv.CircularProgressView
import kotlinx.android.synthetic.main.activity_friends.*
import ru.asshands.softwire.vkfriends.R
import ru.asshands.softwire.vkfriends.adapters.FriendsAdapter
import ru.asshands.softwire.vkfriends.models.FriendModel
import ru.asshands.softwire.vkfriends.models.VKUser
import ru.asshands.softwire.vkfriends.presenters.FriendsPresenter
import ru.asshands.softwire.vkfriends.views.FriendsView

class FriendsActivity : MvpAppCompatActivity(), FriendsView {


    @InjectPresenter
    lateinit var friendsPresenter: FriendsPresenter

    private lateinit var mCpvWait: CircularProgressView
    private lateinit var mTxtNoItems: TextView
    private lateinit var mRvFriends: RecyclerView
    private val mAdapter = FriendsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        mRvFriends = recycler_friends
        mTxtNoItems = txt_friends_no_items
        mCpvWait = cpv_friends
        val mTxtSearch = txt_friends_search
        mTxtSearch.addTextChangedListener (object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAdapter.filter(s.toString())
            }

        })


        friendsPresenter.loadFriends()

        mRvFriends.adapter = mAdapter
        mRvFriends.layoutManager = LinearLayoutManager(
            applicationContext, RecyclerView.VERTICAL, false)
        mRvFriends.setHasFixedSize(true)

    }

    override fun showError(textResource: Int) {
        mTxtNoItems.text = getString(textResource)
    }

    override fun setupEmptyList() {
        mRvFriends.visibility = View.GONE
        mTxtNoItems.visibility = View.VISIBLE
    }

    override fun setupFriendsList(friendsList: List<VKUser>) {
        mRvFriends.visibility = View.VISIBLE
        mTxtNoItems.visibility = View.GONE

        mAdapter.setupFriends(friendsList)
    }

    override fun startLoading() {
        mRvFriends.visibility = View.GONE
        mTxtNoItems.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mCpvWait.visibility = View.GONE
    }
}
