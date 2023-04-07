package com.joaoparedes.instagram.register.data

import com.joaoparedes.instagram.common.model.UserAuth

interface RegisterCallback {
    fun onSuccess()
    fun onFailure(message: String)
    fun onComplete()
}