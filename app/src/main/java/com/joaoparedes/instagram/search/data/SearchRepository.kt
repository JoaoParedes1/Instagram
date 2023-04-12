package com.joaoparedes.instagram.search.data

import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.User
import com.joaoparedes.instagram.common.model.UserAuth
import com.joaoparedes.instagram.splash.data.FireSplashDataSource

class SearchRepository(private val dataSource: SearchDataSource) {

    fun fetchUsers(name: String, callback: RequestCallback<List<User>>) {
        dataSource.fetchUsers(name, object : RequestCallback<List<User>> {
            override fun onSuccess(data: List<User>) {
                callback.onSuccess(data)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }

            override fun onComplete() {
                callback.onComplete()
            }

        })
    }

}