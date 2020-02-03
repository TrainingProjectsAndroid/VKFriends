package ru.asshands.softwire.vkfriends.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import de.hdodenhof.circleimageview.CircleImageView
import ru.asshands.softwire.vkfriends.R
import ru.asshands.softwire.vkfriends.models.FriendModel
import ru.asshands.softwire.vkfriends.models.VKUser

class FriendsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mSourceList: ArrayList<VKUser> = ArrayList()
    private var mFriendsList: ArrayList<VKUser> = ArrayList()

    fun setupFriends(friendList: List<VKUser>) {

        mSourceList.clear()
        mSourceList.addAll(friendList)
        filter(query = "")
    }

    fun filter(query: String) {
        mFriendsList.clear()
        mSourceList.forEach {
            if (it.firstName.contains(query, ignoreCase = true) ||
                it.lastName.contains(query, ignoreCase = true)
            ) {
                mFriendsList.add(it)
            } else {
                it.city.let { city ->
                    if (city.contains(query, ignoreCase = true)) {
                        mFriendsList.add(it)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cell_friends, parent, false)
        return FriendsViewHolder(itemView)
    }

    override fun getItemCount() = mFriendsList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendsViewHolder) {
            holder.bind(mFriendsList[position])
        }
    }
}


class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    private var mCivAvatar = itemView.findViewById<CircleImageView>(R.id.friends_civ_avatar)
    private var mTxtUsername = itemView.findViewById<TextView>(R.id.friends_txt_username)
    private var mTxtCity = itemView.findViewById<TextView>(R.id.friends_txt_city)
    private var mImgOnline = itemView.findViewById<View>(R.id.friends_img_online)


    fun bind(friendModel: VKUser) {

        friendModel.photo.let { url ->
            Picasso.get().load(url).into(mCivAvatar)
        }


        mTxtUsername.text = "${friendModel.firstName} ${friendModel.lastName}"
        mTxtCity.text = itemView.context.getString(R.string.friends_no_city)
        friendModel.city.let { mTxtCity.text = it }

        if (friendModel.online == 1) {
            mImgOnline.visibility = View.VISIBLE
        } else {
            mImgOnline.visibility = View.GONE
        }
    }
}