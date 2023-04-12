package com.joaoparedes.instagram.search.data

import android.os.Handler
import android.os.Looper
import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Database
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.User
import com.joaoparedes.instagram.common.model.UserAuth

class SearchFakeRemoteDataSource: SearchDataSource {

    override fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        Handler(Looper.getMainLooper()).postDelayed({

//            val users = Database.usersAuth.filter { it.name.lowercase().startsWith(name.lowercase()) && Database.sessionAuth!!.uuid != it.uuid }

//            callback.onSuccess(users.toList())
//            callback.onComplete()
        }, 2000)
    }

}