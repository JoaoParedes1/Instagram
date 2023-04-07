package com.joaoparedes.instagram.login.data

import com.joaoparedes.instagram.common.model.UserAuth

interface LoginCallback {
    fun onSuccess(userAuth: UserAuth)
    fun onFailure(message: String)
    fun onComplete()
}