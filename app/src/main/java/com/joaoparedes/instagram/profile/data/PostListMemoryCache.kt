package com.joaoparedes.instagram.profile.data

import com.joaoparedes.instagram.common.base.Cache
import com.joaoparedes.instagram.common.model.Post

object PostListMemoryCache : Cache<List<Post>>{

    private var posts: List<Post>? = null

    override fun isCached(): Boolean {
        return posts != null
    }

    override fun get(key: String): List<Post>? {
        return posts
    }

    override fun put(data: List<Post>?) {
        posts = data
    }
}