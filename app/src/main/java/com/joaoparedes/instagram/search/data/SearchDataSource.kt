package com.joaoparedes.instagram.search.data

import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.User
import com.joaoparedes.instagram.common.model.UserAuth

interface SearchDataSource {
    fun fetchUsers(name: String, callback: RequestCallback<List<User>>)
}