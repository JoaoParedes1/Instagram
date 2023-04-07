package com.joaoparedes.instagram.splash

import com.joaoparedes.instagram.common.base.BasePresenter
import com.joaoparedes.instagram.common.base.BaseView

interface Splash {
    interface Presenter : BasePresenter {
        fun authenticated()
    }

    interface View : BaseView<Presenter> {
        fun goToMainScreen()
        fun goToLoginScreen()
    }
}