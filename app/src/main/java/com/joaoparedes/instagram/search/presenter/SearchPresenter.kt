package com.joaoparedes.instagram.search.presenter

import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.User
import com.joaoparedes.instagram.common.model.UserAuth
import com.joaoparedes.instagram.search.Search
import com.joaoparedes.instagram.search.data.SearchRepository

class SearchPresenter(
    private var view: Search.View?,
    private var repository: SearchRepository
    ): Search.Presenter {

    override fun fetchUsers(name: String) {
        view?.showProgress(true)
        repository.fetchUsers(name, object : RequestCallback<List<User>> {
            override fun onSuccess(data: List<User>) {
                if (data.isEmpty()) {
                    view?.displayEmptyUsers()
                } else {
                    view?.displayFullUsers(data)
                }
            }
            override fun onFailure(message: String) {
                view?.displayEmptyUsers()
            }

            override fun onComplete() {
                view?.showProgress(false)
            }
        })
    }

    override fun onDestroy() {
        view = null
    }
}