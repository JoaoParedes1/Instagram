package com.joaoparedes.instagram.profile.data

import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.UserAuth
import java.lang.UnsupportedOperationException

interface ProfileDataSource {

    fun fetchUserProfile(userUUID: String, callback: RequestCallback<Pair<UserAuth, Boolean?>>)

    fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>)

    fun followUser(userUUID: String, isFollow: Boolean, callback: RequestCallback<Boolean>) { throw UnsupportedOperationException() }

    fun fetchSession(): UserAuth { throw UnsupportedOperationException() }

    fun putUser(response: Pair<UserAuth, Boolean?>) { throw UnsupportedOperationException() }

    fun putPosts(response: List<Post>?) { throw UnsupportedOperationException() }
}