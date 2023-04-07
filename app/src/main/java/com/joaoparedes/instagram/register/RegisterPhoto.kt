package com.joaoparedes.instagram.register

import android.net.Uri
import androidx.annotation.StringRes
import com.joaoparedes.instagram.common.base.BasePresenter
import com.joaoparedes.instagram.common.base.BaseView

interface RegisterPhoto {

    interface Presenter : BasePresenter {
        fun updateUser(photoUri: Uri)
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun onUpdateFailure(message: String)
        fun onUpdateSuccess()
    }
}