package com.joaoparedes.instagram.profile.data

import android.os.Handler
import android.os.Looper
import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Database
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.UserAuth

class ProfileFakeRemoteDataSource: ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<UserAuth, Boolean?>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val userAuth = Database.usersAuth.firstOrNull { userUUID == it.uuid }

            if (userAuth != null) {
                if (userAuth == Database.sessionAuth) {
                    callback.onSuccess(Pair(userAuth, null))
                } else {
                    val following = Database.followers[Database.sessionAuth!!.uuid]

                    val destUser = following?.firstOrNull { it == userUUID }

                    callback.onSuccess(Pair(userAuth, destUser != null))
                }

            } else {
                callback.onFailure("Usuario não encontrado")
            }

            callback.onComplete()
        }, 2000)
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        Handler(Looper.getMainLooper()).postDelayed({

            val posts = Database.posts[userUUID]

            callback.onSuccess(posts?.toList() ?: emptyList())

            callback.onComplete()
        }, 2000)
    }

    override fun followUser(userUUID: String,isFollow: Boolean,callback: RequestCallback<Boolean>) {
        Handler(Looper.getMainLooper()).postDelayed({

            var followers = Database.followers[Database.sessionAuth!!.uuid]

            if (followers == null) {
                followers = mutableSetOf()
                Database.followers[Database.sessionAuth!!.uuid] = followers
            }

            if (isFollow) {
                Database.followers[Database.sessionAuth!!.uuid]!!.add(userUUID)
            } else {
                Database.followers[Database.sessionAuth!!.uuid]!!.remove(userUUID)
            }

            callback.onSuccess(true)
            callback.onComplete()

        }, 500)
    }
}