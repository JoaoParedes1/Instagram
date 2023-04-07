package com.joaoparedes.instagram.register

import androidx.annotation.StringRes
import com.joaoparedes.instagram.common.base.BasePresenter
import com.joaoparedes.instagram.common.base.BaseView

interface RegisterNameAndPassword {

    interface Presenter : BasePresenter {
        fun create(email: String, name: String, password: String, confirm: String)
    }

    interface View: BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameFailure(@StringRes nameError: Int?)
        fun displayPasswordFailure(@StringRes emailError: Int?)
        fun onCreateFailure(message: String)
        fun onCreateSuccess(name: String)
    }
}