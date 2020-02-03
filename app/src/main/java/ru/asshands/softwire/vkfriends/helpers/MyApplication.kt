package ru.asshands.softwire.vkfriends.helpers

import android.app.Application
import com.vk.api.sdk.VK

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        VK.initialize(applicationContext)
    }
}