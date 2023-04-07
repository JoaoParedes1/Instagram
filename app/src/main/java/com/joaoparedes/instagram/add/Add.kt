package com.joaoparedes.instagram.add

import android.net.Uri
import com.joaoparedes.instagram.common.base.BasePresenter
import com.joaoparedes.instagram.common.base.BaseView

interface Add {

    interface Presenter: BasePresenter {
        fun createPost(uri: Uri, caption: String)
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayRequestSuccess()
        fun displayRequestFailure(message: String)
    }
}