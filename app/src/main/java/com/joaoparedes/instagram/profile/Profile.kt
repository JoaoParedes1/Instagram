package com.joaoparedes.instagram.profile

import com.joaoparedes.instagram.common.base.BasePresenter
import com.joaoparedes.instagram.common.base.BaseView
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.common.model.UserAuth

interface Profile {

    interface Presenter: BasePresenter {
        fun fetchUserProfile(uuid: String?)
        fun fetchUserPosts(uuid: String?)
        fun followUser(uuid: String?, follow: Boolean)
        fun clear()
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayUserProfile(user: Pair<UserAuth, Boolean?>)
        fun displayRequestFailure(message: String)
        fun displayEmptyPosts()
        fun displayFullPosts(posts: List<Post>)
    }
}