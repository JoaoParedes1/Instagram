package com.joaoparedes.instagram.search

import com.joaoparedes.instagram.common.base.BasePresenter
import com.joaoparedes.instagram.common.base.BaseView
import com.joaoparedes.instagram.common.model.UserAuth

interface Search {

    interface Presenter: BasePresenter {
        fun fetchUsers(name: String)
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayFullUsers(users: List<UserAuth>)
        fun displayEmptyUsers()
    }
}