package com.joaoparedes.instagram.add.data

import android.net.Uri
import com.joaoparedes.instagram.common.base.RequestCallback
import com.joaoparedes.instagram.common.model.UserAuth

interface AddDataSource {

    fun createPost(userUUID: String, uri: Uri, caption: String, callback: RequestCallback<Boolean>) { throw UnsupportedOperationException() }

    fun fetchSession() : UserAuth { throw UnsupportedOperationException() }
}