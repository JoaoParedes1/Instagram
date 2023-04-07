package com.joaoparedes.instagram.post

import android.net.Uri
import com.joaoparedes.instagram.common.base.BasePresenter
import com.joaoparedes.instagram.common.base.BaseView

interface Post {

    interface Presenter: BasePresenter {
        fun selectUri(uri: Uri)
        fun getSelectedUri(): Uri?
        fun fetchPictures()
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayFullPictures(posts: List<Uri>)
        fun displayEmptyPicture()
        fun displayRequestFailure(message: String)
    }
}