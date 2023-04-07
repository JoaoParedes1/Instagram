package com.joaoparedes.instagram.register.presentation

import android.util.Patterns
import com.joaoparedes.instagram.R
import com.joaoparedes.instagram.register.RegisterEmail
import com.joaoparedes.instagram.register.data.RegisterCallback
import com.joaoparedes.instagram.register.data.RegisterRepository

class RegisterEmailPresenter(
    private var view: RegisterEmail.View?,
    private var repository: RegisterRepository
) : RegisterEmail.Presenter {

    override fun create(email: String) {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if(!isEmailValid) {
            view?.displayEmailFailure(R.string.invalid_email)
        } else {
            view?.displayEmailFailure(null)
        }

        if(isEmailValid) {
            view?.showProgress(true)

            repository.create(email, object : RegisterCallback {
                override fun onSuccess() {
                    view?.goToNameAndPasswordScreen(email)
                }

                override fun onFailure(message: String) {
                    view?.onEmailFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }

            })
        }
    }

    override fun onDestroy() {
        view = null
    }


}