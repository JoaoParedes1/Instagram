package com.joaoparedes.instagram.home.presenter

import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.Post
import com.joaoparedes.instagram.home.Home
import com.joaoparedes.instagram.home.data.HomeRepository

class HomePresenter(
    private var view: Home.View?,
    private var repository: HomeRepository
) : Home.Presenter {

    override fun fetchFeed() {
        view?.showProgress(true)
        repository.fetchFeed(object : RequestCallback<List<Post>> {
            override fun onSuccess(data: List<Post>) {
                if (data.isEmpty()) {
                    view?.displayEmptyPosts()
                } else {
                    view?.displayFullPosts(data)
                }
            }

            override fun onFailure(message: String) {
                view?.displayRequestFailure(message)
            }

            override fun onComplete() {
                view?.showProgress(false)
            }

        })
    }

    override fun clear() {
        repository.clearCache()
    }

    override fun onDestroy() {
        view = null
    }
}